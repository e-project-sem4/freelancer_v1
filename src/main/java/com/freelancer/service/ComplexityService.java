package com.freelancer.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.freelancer.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.freelancer.model.Complexity;
import com.freelancer.model.ResponseObject;
import com.freelancer.repository.ComplexityRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.google.gson.Gson;

@Service
public class ComplexityService {

	Logger logger = ConfigLog.getLogger(ComplexityService.class);

	Gson gson = new Gson();
	@Autowired
	private ComplexityRepository complexityRepository;





	// add
	public ResponseObject save(Complexity obj) {
		String message = "not success";
		logger.info("call to Create obj" + obj.toString());
		obj.setCreateAt(DateUtil.getTimeLongCurrent());
		obj.setStatus(1);
		Complexity result = complexityRepository.save(obj);
		if (result != null) {
			message = "success";
		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
	}

	// delete
	public ResponseObject delete(Long id) {

		logger.info("call to get obj to delete by id: " + id);
		Complexity obj = complexityRepository.getOne(id);
		String message = "can not find obj";
		Complexity result = null;
		if (obj.getId()!=null) {
			obj.setStatus(0);
			result = complexityRepository.save(obj);
			message = "delete success";
			logger.info("delete obj success");
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
		} else {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}

	}

	// update
	public ResponseObject update(Complexity obj, Long id) {
		logger.info("call to get obj to update by id: " + id);
		Complexity obj1 = complexityRepository.getOne(id);
		String message = "can not find obj";
		Complexity result = null;
		if (obj.getId()!=null) {
			if (obj.getComplexityText()!=null){
				obj1.setComplexityText(obj.getComplexityText());
			}
			if (obj.getStatus()!=null){
				obj1.setStatus(obj.getStatus());
			}
			obj1.setUpdateAt(DateUtil.getTimeLongCurrent());
			result = complexityRepository.save(obj1);
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
		Optional<Complexity> obj = complexityRepository.findById(id);
		String message = "can not find obj";
		Complexity obj1 = null;
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
		List<Complexity> list = complexityRepository.findAll();
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message,null, list);

	}

	// Search/list
	public ResponseObject search(String keysearch, int page, int size) {
		logger.info("call to search obj with keysearch: " + keysearch);
		String message = "success";
		List<Complexity> list = complexityRepository.searchObj(keysearch, PageRequest.of(page - 1, size));
		Long total = complexityRepository.countObj(keysearch);
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
	}
}
