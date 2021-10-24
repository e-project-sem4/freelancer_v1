package com.freelancer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freelancer.JwtAuthServiceApp;
import com.freelancer.model.HasSkill;
import com.freelancer.model.Job;
import com.freelancer.model.OtherSkill;
import com.freelancer.model.Proposal;
import com.freelancer.model.ResponseObject;
import com.freelancer.model.User;
import com.freelancer.model.UserFreelancer;
import com.freelancer.repository.JobRepository;
import com.freelancer.repository.OtherSkillRepository;
import com.freelancer.repository.UserFreelancerRepository;
import com.freelancer.repository.UserRepository;
import com.freelancer.search.FreelancerSpecification;
import com.freelancer.search.JobSpecification;
import com.freelancer.search.SearchCriteria;
import com.freelancer.sendmail.SendMailModel;
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
	@Autowired
	private UserFreelancerRepository userFreelancerRepository;

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
		boolean isSendmail = false;
		User user = userRepository.findByUsername(username);
		String message = "not success";
		logger.info("call to Create Job" + jobRepository.toString());
		obj.setCreateAt(DateUtil.getTimeLongCurrent());
		if (user.getUserBusinesses().getId() == null) {
			message = "Account not mapping Business";
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}
		// check số dư tài khoản

		if (user.getBalance() < obj.getPaymentAmount()) {
			obj.setIsPaymentStatus(0);
		} else {
			obj.setIsPaymentStatus(1);
			Double balanceNew = user.getBalance() - obj.getPaymentAmount();
			user.setBalance(balanceNew);
			userRepository.save(user);
			isSendmail=true;
		}

		obj.setUser_business_id(user.getUserBusinesses().getId());
		obj.setStatus(1);
		Job result = jobRepository.save(obj);
		if (isSendmail){
			SendMailModel sendMailModel = new SendMailModel();
			sendMailModel.setJobId(result.getId().toString());
			List<OtherSkill> skillJob = result.getOtherSkills().stream().collect(Collectors.toList());
			ArrayList<Long> idSkillJob = new ArrayList<>();
			for (OtherSkill o: skillJob
				 ) {
				idSkillJob.add(o.getSkill_id());
			}

			Specification<UserFreelancer> specification = Specification.where(null);
			for (Long id : idSkillJob
			) {
				specification = specification.and(new FreelancerSpecification(new SearchCriteria("skill_id", "==skill", id)));
			}

			List<UserFreelancer> userFreelancers= userFreelancerRepository.findAll(specification);
			for (UserFreelancer u: userFreelancers
				 ) {
				JwtAuthServiceApp.listSendMail.add(new SendMailModel(u.getUser().getEmail(), result.getId().toString()));
			}



		}
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
	public ResponseObject update(Job jobUpdate, Long id, String username) {
		User user = userRepository.findByUsername(username);
		logger.info("call to get obj to update by id: " + id);
		Job jobCurrent = jobRepository.getOne(id);
		String message = "can not find obj";
		Job result = null;
		if (jobCurrent.getId() != null) {
			// nếu có thanh đổi trường giá tiền job thì thực hiện trong If, nếu ko
			// thì tiếp tục update các trường khác
			if (jobUpdate.getPaymentAmount() != null) {
				// check trạng thái thanh toán
				if (jobCurrent.getIsPaymentStatus() == 0 || jobCurrent.getIsPaymentStatus() == null) {
					// nếu chưa thanh toán thì update theo yêu cầu và hiện trạng thái chưa
					// thanh toán để user thanh toán sau
					jobCurrent.setPaymentAmount(jobUpdate.getPaymentAmount());
					// set lại trạn thái vẫn chưa thanh toán, tranh trường hợp bị null;
					jobCurrent.setIsPaymentStatus(0);
				} else {// nếu đã thanh toán
					Double difference = jobCurrent.getPaymentAmount() - jobUpdate.getPaymentAmount();
					if (difference >= 0) {// nếu số tiền đã thanh toán lớn hơn hoặc bằng sô tiền đã muốn
											// update
						// cập nhật số tiền của Job bằng số tiền truyền vào
						jobCurrent.setPaymentAmount(jobUpdate.getPaymentAmount());
						// cộng thêm tiền chênh lệch cho User
						user.setBalance(user.getBalance() + difference);
						userRepository.save(user);
					} else { // nếu số tiền đã thanh toán bé hơn số tiền muốn update
						// check số dư
						// nếu số dư lớn bé hơn tiền chênh lệch (nhân -1 để lấy số dương) thì
						// không cập nhật thông tin nữa
						if (user.getBalance() < difference * (-1)) {
							return new ResponseObject(Constant.STATUS_ACTION_FAIL,
									"Số dư không đủ, vui lòng kiểm tra và nạp thêm tiền", result);
						}
						// trường hợp còn lại, số dư lớn hơn tiền chênh lệch (nhân -1 để lấy
						// số dương)
						// cập nhật số tiền muốn update
						jobCurrent.setPaymentAmount(jobUpdate.getPaymentAmount());
						// trừ tiền trong tài khoản Usẻ
						user.setBalance(user.getBalance() - difference * (-1));
						userRepository.save(user);
					}
				}
			}
			if (jobUpdate.getComplexity_id() != null) {
				jobCurrent.setComplexity_id(jobUpdate.getComplexity_id());
			}
			if (jobUpdate.getName() != null && !jobUpdate.getName().isEmpty()) {
				jobCurrent.setName(jobUpdate.getName());
			}
			if (jobUpdate.getDescription() != null && !jobUpdate.getDescription().isEmpty()) {
				jobCurrent.setDescription(jobUpdate.getDescription());
			}
			if (jobUpdate.getExpected_duration_id() != null) {
				jobCurrent.setExpected_duration_id(jobUpdate.getExpected_duration_id());
			}

			if (jobUpdate.getStatus() != null) {
				jobCurrent.setStatus(jobUpdate.getStatus());
			}

			jobCurrent.setUpdateAt(DateUtil.getTimeLongCurrent());
			result = jobRepository.save(jobCurrent);

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
				for (Proposal p : j.getProposals()) {
					if (p != null) {
						p.setUserAccountId(userRepository.findById(
								userFreelancerRepository.findById(p.getUser_freelancer_id()).get().getUser_account_id())
								.get().getId());
						p.setFreeLancerName(userRepository.findById(
								userFreelancerRepository.findById(p.getUser_freelancer_id()).get().getUser_account_id())
								.get().getFullName());
					}
				}
				return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, optionalJob.get());
			}
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);

		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);

	}

	public ResponseObject setIsPaymentStatusJob(Long id) {
		logger.info("call to get setIsPaymentStatusJob by id: " + id);
		Optional<Job> optionalJob = jobRepository.findById(id);
		if (optionalJob.isPresent()) {
			Job rl = optionalJob.get();
			rl.setIsPaymentStatus(1);
			rl.setUpdateAt(DateUtil.getTimeLongCurrent());
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "Thanh toan thanh cong", jobRepository.save(rl));
		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, "Khong tim thay Job can thanh toan", null);
	}

	public ResponseObject getSuitableJob(String username) {
		User user = userRepository.findByUsername(username);
		List<Long> listSkill = new ArrayList<>();
		Specification<Job> specification = Specification.where(null);
		for (HasSkill hasSkill : user.getUserFreelancers().getHasSkills().stream().collect(Collectors.toList())) {
			System.out.println(hasSkill.getSkill_id());
			listSkill.add(hasSkill.getSkill_id());
		}
		for (Long skillId : listSkill) {
			specification = specification.or(new JobSpecification(new SearchCriteria("skill_id", "==skill", skillId)));
		}
		List<Job> list = jobRepository.findAll(specification, PageRequest.of(0, 3)).getContent();
//		if (listSkill.size() > 0) {
//			
//		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "success", list);
	}

}
