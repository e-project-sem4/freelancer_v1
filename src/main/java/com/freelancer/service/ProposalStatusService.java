package com.freelancer.service;

import com.freelancer.model.*;
import com.freelancer.repository.ProposalStatusCatalogRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.freelancer.utils.DateUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
			if (obj.get().getStatus()!=0){
				message = "success";
				logger.info("get obj success");
				return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, obj.get());
			}
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);

		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
	}



	// Search/list
	public ResponseObject search(Specification<ProposalStatusCatalog> specification, int page, int size, int sort) {
		List<ProposalStatusCatalog> list = null;
		if (page>0&&size>0&&(sort>2||sort<1)){
			list = proposalStatusCatalogRepository.findAll(specification,PageRequest.of(page-1,size)).getContent();
		}else if (page>0&&size>0&&sort==1){
			list = proposalStatusCatalogRepository.findAll(specification,PageRequest.of(page-1,size, Sort.by("createAt").descending())).getContent();
		}else if (page>0&&size>0&&sort==2){
			list = proposalStatusCatalogRepository.findAll(specification,PageRequest.of(page-1,size, Sort.by("createAt").descending())).getContent();
		}else if (page==0&&size==0&&sort==0){
			list = proposalStatusCatalogRepository.findAll(specification);
		}

		Long total = Long.valueOf(proposalStatusCatalogRepository.findAll(specification).size());
		String message = "success";
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
	}
}
