package com.freelancer.service;

import com.freelancer.JwtAuthServiceApp;
import com.freelancer.model.*;
import com.freelancer.repository.*;
import com.freelancer.sendmail.SendMailModel;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.freelancer.utils.DateUtil;
import com.freelancer.utils.UtilService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

	Logger logger = ConfigLog.getLogger(ExpectedDurationService.class);

	Gson gson = new Gson();
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private ContractRepository contractRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserFreelancerRepository userFreelancerRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private ProposalRepository proposalRepository;
	@Autowired
	private ChatKeyUserRepository chatKeyUserRepository;

	// add
	public ResponseObject save(Contract obj, String username) {
		String message = "not success";
		logger.info("call to Create obj" + obj.toString());
		User user = userRepository.findByUsername(username);
		Long currentBusinessId = user.getUserBusinesses().getId();
		Long currentFreelancerId = user.getUserFreelancers().getId();
		Optional<Proposal> currentProposalOptional = proposalRepository.findById(obj.getProposal_id());
		if (!currentProposalOptional.isPresent()) {
			message = "This Proposal no longer exists";
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}
		Proposal currentProposal = currentProposalOptional.get();
		Long proposalFreelancerId = currentProposal.getUser_freelancer_id();
		Optional<Job> currentJobOptional = jobRepository.findById(currentProposal.getJob_id());
		if (!currentJobOptional.isPresent()) {
			message = "This Job no longer exists";
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}
		Long currentBusinessJobId = currentJobOptional.get().getUser_business_id();
		if (currentBusinessId == currentBusinessJobId && currentFreelancerId != proposalFreelancerId) {
			//check gi?? job v?? gi?? proposal
			Double difference = currentJobOptional.get().getPaymentAmount() - obj.getPaymentAmount();
			if (difference >= 0) {// n????u s???? ti????n ??a?? thanh toa??n l????n h??n ho????c b????ng s?? ti????n mu????n update
				// c????p nh????t s???? ti????n cu??a Job b????ng s???? ti????n truy????n va??o
				currentJobOptional.get().setPaymentAmount(obj.getPaymentAmount());
				currentJobOptional.get().setUpdateAt(DateUtil.getTimeLongCurrent());
				// c????ng th??m ti????n ch??nh l????ch cho User
				user.setBalance(user.getBalance() + difference);
				userRepository.save(user);
				jobRepository.save(currentJobOptional.get());
				if (difference>0){
					//Add transaction
					Transaction transaction = new Transaction();
					transaction.setPrice(difference);
					transaction.setContent("Refund for change price!");
					transaction.setCreateAt(DateUtil.getTimeLongCurrent());
					transaction.setType(Transaction.TransactionType.REFUND);
					transaction.setJob_id(currentJobOptional.get().getId());
					transaction.setUser_account_id(user.getId());
					transactionRepository.save(transaction);
				}


			} else { // n????u s???? ti????n ??a?? thanh toa??n be?? h??n s???? ti????n mu????n update
				// check s???? d??
				// n????u s???? d?? l????n be?? h??n ti????n ch??nh l????ch (nh??n -1 ?????? l????y s???? d????ng) thi??
				// kh??ng c????p nh????t th??ng tin n????a
				if (user.getBalance() < difference * (-1)) {
					return new ResponseObject(Constant.STATUS_ACTION_FAIL,
							"Kh??ng th??? nh???n ????? xu???t n??y. S???? d?? kh??ng ??u??, vui lo??ng ki????m tra va?? na??p th??m ti????n", null);
				}
				// tr??????ng h????p co??n la??i, s???? d?? l????n h??n ti????n ch??nh l????ch (nh??n -1 ?????? l????y s???? d????ng)
				// c????p nh????t s???? ti????n mu????n update
				currentJobOptional.get().setPaymentAmount(obj.getPaymentAmount());
				// tr???? ti????n trong ta??i khoa??n User
				user.setBalance(user.getBalance() - difference * (-1));
				userRepository.save(user);
				jobRepository.save(currentJobOptional.get());

				//Add transaction
				Transaction transaction = new Transaction();
				transaction.setPrice(difference * (-1));
				transaction.setContent("Pay extra for proposal!");
				transaction.setCreateAt(DateUtil.getTimeLongCurrent());
				transaction.setType(Transaction.TransactionType.PAYMENT);
				transaction.setJob_id(currentJobOptional.get().getId());
				transaction.setUser_account_id(user.getId());
				transactionRepository.save(transaction);

			}

			obj.setStartTime(DateUtil.getTimeLongCurrent());
			obj.setStatus(1);
			obj.setUser_business_id(currentBusinessId);
			Contract result = contractRepository.save(obj);
			message = "success";

			//Update status proposal
			Proposal proposalUpdate = proposalRepository.getOne(obj.getProposal_id());
			proposalUpdate.setProposal_status_catalog_id(2L);
			proposalRepository.save(proposalUpdate);

			//Update status job =2 (Doing)
			Job job = jobRepository.getOne(proposalUpdate.getJob_id());
			job.setStatus(2);
			jobRepository.save(job);

			//SendMail Ng?????i ???????c nh???n

			User userAccountFreelancer = userRepository.getOne(userFreelancerRepository.getOne(currentProposal.getUser_freelancer_id()).getUser_account_id());
			JwtAuthServiceApp.listSendMail.add(new SendMailModel(userAccountFreelancer.getEmail(),"Congratulations on getting approved for a job!", currentProposal.getJob_id().toString()));

			//SendMail nh???ng th???ng k ??c nh???n
			for (Proposal p: job.getProposals()
				 ) {

				JwtAuthServiceApp.listSendMail.add(new SendMailModel(p.getUserFreelancer().getUser().getEmail(),"The job you've bid on has already been accepted by someone else :(. Good luck next time!", currentProposal.getJob_id().toString()));

			}

			// t???o chatbox cho freelancer - business
			List<ChatKeyUser> listToSave = new ArrayList<>();
			ChatKeyUser chatKeyUser = new ChatKeyUser(null, currentBusinessJobId, proposalFreelancerId,
					currentJobOptional.get().getId(),
					UtilService.convertRoomKey(proposalFreelancerId, currentBusinessJobId,
							currentJobOptional.get().getId()),
					currentJobOptional.get().getName(), proposalUpdate.getId());
			ChatKeyUser chatKeyUser2 = new ChatKeyUser(null, proposalFreelancerId, currentBusinessJobId,
					currentJobOptional.get().getId(),
					UtilService.convertRoomKey(proposalFreelancerId, currentBusinessJobId,
							currentJobOptional.get().getId()),
					currentJobOptional.get().getName(), proposalUpdate.getId());
			listToSave.add(chatKeyUser2);
			listToSave.add(chatKeyUser);
			chatKeyUserRepository.saveAll(listToSave);
			result.setChatKeyUser(chatKeyUser);
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);

		}
		message = "You are not the post owner";
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, null);
	}

	// get by id
	public ResponseObject getById(Long id) {
		logger.info("call to get obj by id: " + id);
		Optional<Contract> obj = contractRepository.findById(id);
		String message = "can not find obj";
		if (obj.isPresent()) {
			if (obj.get().getStatus() != 0) {
				message = "success";
				logger.info("get obj success");
				return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, obj.get());
			}
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);

		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
	}
}
