package com.freelancer.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freelancer.model.Job;
import com.freelancer.model.OtherSkill;
import com.freelancer.model.ResponseObject;
import com.freelancer.model.User;
import com.freelancer.repository.JobRepository;
import com.freelancer.repository.OtherSkillRepository;
import com.freelancer.repository.UserRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.freelancer.utils.DateUtil;

@Service
public class JobService {
	Logger logger = ConfigLog.getLogger(JobService.class);
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private OtherSkillRepository otherSkillRepository;
	@Autowired
	private UserRepository userRepository;

	public ResponseObject search(Specification<Job> specification, int page, int size, int sort) {
		List<Job> list = null;
		if (page > 0 && size > 0 && (sort > 4 || sort < 1)) {
			list = jobRepository.findAll(specification, PageRequest.of(page - 1, size)).getContent();
		} else if (page > 0 && size > 0 && sort == 1) {
			list = jobRepository
					.findAll(specification, PageRequest.of(page - 1, size, Sort.by("createAt").descending()))
					.getContent();
		} else if (page > 0 && size > 0 && sort == 2) {
			list = jobRepository.findAll(specification, PageRequest.of(page - 1, size, Sort.by("createAt").ascending()))
					.getContent();
		} else if (page > 0 && size > 0 && sort == 3) {
			list = jobRepository
					.findAll(specification, PageRequest.of(page - 1, size, Sort.by("paymentAmount").descending()))
					.getContent();
		} else if (page > 0 && size > 0 && sort == 4) {
			list = jobRepository
					.findAll(specification, PageRequest.of(page - 1, size, Sort.by("paymentAmount").ascending()))
					.getContent();
		} else if (page == 0 && size == 0 && sort == 0) {
			list = jobRepository.findAll(specification);
		}
		for (Job j : list) {
			j.getUserBusiness().getUser().setPassword(null);
			j.getUserBusiness().getUser().setPhone(null);
		}
		Long total = Long.valueOf(jobRepository.findAll(specification).size());
		String message = "success";
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
	}

	// add
	@Transactional
	public ResponseObject save(Job obj, String username) {
		User user = userRepository.findByUsername(username);
		String message = "not success";
		logger.info("call to Create Job" + jobRepository.toString());
		obj.setCreateAt(DateUtil.getTimeLongCurrent());
		if (user.getUserBusinesses().getId() == null) {
			message = "Account not mapping Business";
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}
		obj.setUser_business_id(user.getUserBusinesses().getId());
		obj.setStatus(1);
		Job result = jobRepository.save(obj);
		for (OtherSkill o : obj.getOtherSkills()) {
			o.setJob_id(result.getId());
			otherSkillRepository.save(o);
		}
		if (result != null) {
			message = "success";
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
	}

	// delete
	public ResponseObject delete(Long id) {
		logger.info("call to get obj to delete by id: " + id);
		Job obj = jobRepository.getOne(id);
		String message = "can not find obj";
		Job result = null;
		if (obj.getId() != null) {
			obj.setStatus(0);
			result = jobRepository.save(obj);
			message = "delete success";
			logger.info("delete obj success");
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
		} else {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}
	}

	// update
	public ResponseObject update(Job obj, Long id) {

		logger.info("call to get obj to update by id: " + id);
		Job obj1 = jobRepository.getOne(id);
		String message = "can not find obj";
		Job result = null;
		if (obj.getId() != null) {
			if (obj.getComplexity_id() != null) {
				obj1.setComplexity_id(obj.getComplexity_id());
			}
			if (obj.getName() != null) {
				obj1.setName(obj.getName());
			}
			if (obj.getDescription() != null) {
				obj1.setDescription(obj.getDescription());
			}
			if (obj.getExpected_duration_id() != null) {
				obj1.setExpected_duration_id(obj.getExpected_duration_id());
			}
			if (obj.getPaymentAmount() != null) {
				obj1.setPaymentAmount(obj.getPaymentAmount());
			}

			if (obj.getStatus() != null) {
				obj1.setStatus(obj.getStatus());
			}
			obj1.setUpdateAt(DateUtil.getTimeLongCurrent());
			result = jobRepository.save(obj1);
			message = "update success";
			logger.info("update obj success");

			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
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
			if (optionalJob.get().getStatus() != 0) {
				message = "success";
				logger.info("get Job success");
				Job j = optionalJob.get();
				j.getUserBusiness().getUser().setPassword(null);
				j.getUserBusiness().getUser().setPhone(null);
				return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, optionalJob.get());
			}
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);

		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);

	}
}
