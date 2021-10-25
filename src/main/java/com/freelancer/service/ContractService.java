package com.freelancer.service;

import com.freelancer.model.*;
import com.freelancer.repository.ChatKeyUserRepository;
import com.freelancer.repository.ContractRepository;
import com.freelancer.repository.JobRepository;
import com.freelancer.repository.ProposalRepository;
import com.freelancer.repository.UserRepository;
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
			obj.setStartTime(DateUtil.getTimeLongCurrent());
			obj.setStatus(1);
			obj.setUser_business_id(currentBusinessId);
			Contract result = contractRepository.save(obj);
			message = "success";
			Proposal proposalUpdate = proposalRepository.getOne(obj.getProposal_id());
			proposalUpdate.setProposal_status_catalog_id(2L);
			proposalRepository.save(proposalUpdate);
			// tạo chatbox cho freelancer - business
			List<ChatKeyUser> listToSave = new ArrayList<>();
			ChatKeyUser chatKeyUser = new ChatKeyUser(null, currentBusinessJobId, proposalFreelancerId,
					currentJobOptional.get().getId(),
					UtilService.convertRoomKey(currentBusinessJobId, proposalFreelancerId,
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
