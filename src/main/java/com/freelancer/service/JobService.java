package com.freelancer.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.freelancer.model.Job;
import com.freelancer.model.ResponseObject;
import com.freelancer.repository.JobRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;

@Service
public class JobService {
	Logger logger = ConfigLog.getLogger(JobService.class);
	@Autowired
	private JobRepository jobRepository;

	public ResponseObject search(String keysearch, int page, int size) {
		logger.info("call to search Job with key : " + keysearch);
		List<Job> list = jobRepository.searchJob(keysearch, PageRequest.of(page - 1, size));
		Long total = jobRepository.countJob(keysearch);
		String message = "success";
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
	}

	// add
	public ResponseObject save(Job obj) {
		String message = "not success";
		logger.info("call to Create Job" + jobRepository.toString());
		Job result = jobRepository.save(obj);
		if (result != null) {
			message = "success";
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
	}

	// delete
	public ResponseObject delete(Long id) {
		String message = "can not find job";
		logger.info("call to get job to delete by id: " + id);
		Optional<Job> optionalJob = jobRepository.findById(id);
		if (optionalJob.isPresent()) {
			jobRepository.deleteById(id);
			message = "delete success";
			logger.info("delete job success");
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, null);
		} else {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}

	}

	public static void main(String[] args) {
		String a = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(4576834753L));
		System.out.println(a);
	}

	// update
	public ResponseObject update(Job obj, Long id) {
		String message = "can not Job";
		logger.info("call to get Job to update by id: " + id);
		Optional<Job> optionalJob = jobRepository.findById(id);
		if (optionalJob.isPresent()) {
			message = "update success";
			logger.info("update Job success");
			Job result = optionalJob.get();
			result.setName(obj.getName());
			result.setDescription(obj.getDescription());
			result.setSkill_id(obj.getSkill_id());
			result.setComplexity_id(obj.getComplexity_id());
			result.setExpected_duration_id(obj.getExpected_duration_id());
			result.setPaymentAmount(obj.getPaymentAmount());
			Job success = jobRepository.save(result);
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, success);
		} else {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}

	}

	// get by id
	public ResponseObject getById(Long id) {
		logger.info("call to get Job by id: " + id);
		Optional<Job> optionalJob = jobRepository.findById(id);
		String message = "can not find Job";
		if (optionalJob.isPresent()) {
			message = "success";
			optionalJob.get();
			logger.info("get Job success");
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, optionalJob.get());
		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
	}
}
