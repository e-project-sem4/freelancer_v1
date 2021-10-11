package com.freelancer.service;

import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.ProposalStatusCatalog;
import com.freelancer.model.ResponseObject;
import com.freelancer.repository.ProposalStatusCatalogRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProposalStatusService {
	Logger logger = ConfigLog.getLogger(ProposalStatusService.class);

	Gson gson = new Gson();
	@Autowired
	private ProposalStatusCatalogRepository proposalStatusCatalogRepository;

	public ResponseObject fillAll(){
		String message = "success";
		List<ProposalStatusCatalog> list = proposalStatusCatalogRepository.findAll();
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message,null, list);

	}

	// add
	public ResponseObject save(ProposalStatusCatalog proposalStatusCatalog) {
		String message = "not success";
		logger.info("call to Create Proposal status" + proposalStatusCatalog.toString());
		ProposalStatusCatalog result = proposalStatusCatalogRepository.save(proposalStatusCatalog);
		if (result != null) {
			message = "success";
		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
	}

	// delete
	public ResponseObject delete(Long id) {

		logger.info("call to get Protosal Status to delete by id: " + id);
		Optional<ProposalStatusCatalog> optionalProposalStatusCatalog = proposalStatusCatalogRepository.findById(id);
		String message = "can not find Proposal status";
		if (optionalProposalStatusCatalog.isPresent()) {
			proposalStatusCatalogRepository.deleteById(id);
			message = "delete success";
			logger.info("delete Proposal status success");
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, null);
		} else {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}

	}

	// update
	public ResponseObject update(ProposalStatusCatalog proposalStatusCatalog, Long id) {
		logger.info("call to get Proposal status to update by id: " + id);
		Optional<ProposalStatusCatalog> optionalProposalStatusCatalog = proposalStatusCatalogRepository.findById(id);
		String message = "can not Proposal status";
		ProposalStatusCatalog result = null;
		if (optionalProposalStatusCatalog.isPresent()) {
			result = proposalStatusCatalogRepository.save(proposalStatusCatalog);
			message = "update success";
			logger.info("update Proposal status success");
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
		} else {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}

	}

	// get by id
	public ResponseObject getById(Long id) {
		logger.info("call to get Proposal status by id: " + id);
		Optional<ProposalStatusCatalog> optionalProposalStatusCatalog = proposalStatusCatalogRepository.findById(id);
		String message = "can not find Proposal status";
		ProposalStatusCatalog proposalStatusCatalog = null;
		if (optionalProposalStatusCatalog.isPresent()) {
			message = "success";
			proposalStatusCatalog = optionalProposalStatusCatalog.get();
			logger.info("get Proposal status success");
		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, proposalStatusCatalog);
	}

	// Search/list
	public ResponseObject search(String keysearch, int page, int size) {
		logger.info("call to search Proposal status with keysearch: " + keysearch);
		String message = "success";
		List<ProposalStatusCatalog> list = proposalStatusCatalogRepository.searchProposalSC(keysearch,
				PageRequest.of(page - 1, size));
		Long total = proposalStatusCatalogRepository.countProposalSC(keysearch);
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
	}
}
