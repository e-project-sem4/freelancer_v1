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
			//check giá job và giá proposal
			Double difference = currentJobOptional.get().getPaymentAmount() - obj.getPaymentAmount();
			if (difference >= 0) {// nếu số tiền đã thanh toán lớn hơn hoặc bằng sô tiền muốn update
				// cập nhật số tiền của Job bằng số tiền truyền vào
				currentJobOptional.get().setPaymentAmount(obj.getPaymentAmount());
				currentJobOptional.get().setUpdateAt(DateUtil.getTimeLongCurrent());
				// cộng thêm tiền chênh lệch cho User
				user.setBalance(user.getBalance() + difference);
				userRepository.save(user);
				jobRepository.save(currentJobOptional.get());
			} else { // nếu số tiền đã thanh toán bé hơn số tiền muốn update
				// check số dư
				// nếu số dư lớn bé hơn tiền chênh lệch (nhân -1 để lấy số dương) thì
				// không cập nhật thông tin nữa
				if (user.getBalance() < difference * (-1)) {
					return new ResponseObject(Constant.STATUS_ACTION_FAIL,
							"Không thẻ nhận đề xuất này. Số dư không đủ, vui lòng kiểm tra và nạp thêm tiền", null);
				}
				// trường hợp còn lại, số dư lớn hơn tiền chênh lệch (nhân -1 để lấy số dương)
				// cập nhật số tiền muốn update
				currentJobOptional.get().setPaymentAmount(obj.getPaymentAmount());
				// trừ tiền trong tài khoản Usẻ
				user.setBalance(user.getBalance() - difference * (-1));
				userRepository.save(user);
				jobRepository.save(currentJobOptional.get());
			}

			obj.setStartTime(DateUtil.getTimeLongCurrent());
			obj.setStatus(1);
			obj.setUser_business_id(currentBusinessId);
			Contract result = contractRepository.save(obj);
			message = "success";
			Proposal proposalUpdate = proposalRepository.getOne(obj.getProposal_id());
			proposalUpdate.setProposal_status_catalog_id(2L);
			proposalRepository.save(proposalUpdate);

			//Update status job =2 (Doing)
			Job job = jobRepository.getOne(proposalUpdate.getJob_id());
			job.setStatus(2);
			jobRepository.save(job);

			//SendMail

			User userAccountFreelancer = userRepository.getOne(userFreelancerRepository.getOne(currentProposal.getUser_freelancer_id()).getUser_account_id());
			JwtAuthServiceApp.listSendMail.add(new SendMailModel(userAccountFreelancer.getEmail(),"Congratulations on getting approved for a job!", currentProposal.getJob_id().toString()));

			// tạo chatbox cho freelancer - business
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
