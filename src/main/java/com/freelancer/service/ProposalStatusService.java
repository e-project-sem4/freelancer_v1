package com.freelancer.service;

import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.Proposal;
import com.freelancer.model.ProposalStatusCatalog;
import com.freelancer.model.ResponseObject;
import com.freelancer.repository.ProposalStatusCatalogRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.freelancer.utils.DateUtil;
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

	// add
	public ResponseObject save(ProposalStatusCatalog obj) {
		String message = "not success";
		logger.info("call to Create obj" + obj.toString());
		obj.setCreateAt(DateUtil.getTimeLongCurrent());
		obj.setStatus(1);
		ProposalStatusCatalog result = proposalStatusCatalogRepository.save(obj);
		if (result != null) {
			message = "success";
		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
	}

	// delete
	public ResponseObject delete(Long id) {

		logger.info("call to get obj to delete by id: " + id);
		ProposalStatusCatalog obj = proposalStatusCatalogRepository.getOne(id);
		String message = "can not find obj";
		ProposalStatusCatalog result = null;
		if (obj.getId()!=null) {
			obj.setStatus(0);
			result = proposalStatusCatalogRepository.save(obj);
			message = "delete success";
			logger.info("delete obj success");
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
		} else {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}

	}

	// update
	public ResponseObject update(ProposalStatusCatalog obj, Long id) {
		logger.info("call to get obj to update by id: " + id);
		ProposalStatusCatalog obj1 = proposalStatusCatalogRepository.getOne(id);
		String message = "can not find obj";
		ProposalStatusCatalog result = null;
		if (obj.getId()!=null) {
			if (obj.getStatusName()!=null){
				obj1.setStatusName(obj.getStatusName());
			}
			if (obj.getStatus()!=null){
				obj1.setStatus(obj.getStatus());
			}
			obj1.setUpdateAt(DateUtil.getTimeLongCurrent());
			result = proposalStatusCatalogRepository.save(obj1);
			message = "update success";
			logger.info("update obj success");
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
		} else {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}

	}

	// get by id
	public ResponseObject getById(Long id) {
		logger.info("call to get obj by id: " + id);
		Optional<ProposalStatusCatalog> obj = proposalStatusCatalogRepository.findById(id);
		String message = "can not find obj";
		ProposalStatusCatalog obj1 = null;
		if (obj.isPresent()) {
			message = "success";
			obj1 = obj.get();
			logger.info("get obj success");
		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, obj1);
	}

	//All
	public ResponseObject findAll(){
		String message = "success";
		List<ProposalStatusCatalog> list = proposalStatusCatalogRepository.findAll();
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message,null, list);

	}

	// Search/list
	public ResponseObject search(String keysearch, int page, int size) {
		logger.info("call to search obj with keysearch: " + keysearch);
		String message = "success";
		List<ProposalStatusCatalog> list = proposalStatusCatalogRepository.searchObj(keysearch, PageRequest.of(page - 1, size));
		Long total = proposalStatusCatalogRepository.countObj(keysearch);
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
	}
}
