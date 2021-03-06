package com.freelancer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.freelancer.model.*;
import com.freelancer.repository.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freelancer.JwtAuthServiceApp;
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
	private UserBusinessRepository userBusinessRepository;
	@Autowired
	private TransactionRepository transactionRepository;
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
		// check s??? d?? t??i kho???n

		if (user.getBalance() < obj.getPaymentAmount()) {
			obj.setIsPaymentStatus(0);
			obj.setStatus(-1);
		} else {
			obj.setIsPaymentStatus(1);
			obj.setStatus(1);
			Double balanceNew = user.getBalance() - obj.getPaymentAmount();
			user.setBalance(balanceNew);
			userRepository.save(user);
			isSendmail=true;
		}

		obj.setUser_business_id(user.getUserBusinesses().getId());
		Job result = jobRepository.save(obj);
		if (isSendmail){
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
				JwtAuthServiceApp.listSendMail.add(new SendMailModel(u.getUser().getEmail(),"We found 1 job matching your skills.", result.getId().toString()));
			}

			//Add transaction
			Transaction transaction = new Transaction();
			transaction.setPrice(obj.getPaymentAmount());
			transaction.setContent("Pay for create job!");
			transaction.setCreateAt(DateUtil.getTimeLongCurrent());
			transaction.setType(Transaction.TransactionType.PAYMENT);
			transaction.setJob_id(result.getId());
			transaction.setUser_account_id(user.getId());
			transactionRepository.save(transaction);



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
			//Refund
			Optional<User> user = userRepository.findById(obj.getUserBusiness().getUser_account_id());
			if (!user.isPresent()){
				message = "can not find user";
			}
			user.get().setBalance(user.get().getBalance()+obj.getPaymentAmount());
			userRepository.save(user.get());

			//Add transaction
			Transaction transaction = new Transaction();
			transaction.setPrice(obj.getPaymentAmount());
			transaction.setContent("Refund for delete job!");
			transaction.setCreateAt(DateUtil.getTimeLongCurrent());
			transaction.setType(Transaction.TransactionType.REFUND);
			transaction.setJob_id(obj.getId());
			transaction.setUser_account_id(user.get().getId());
			transactionRepository.save(transaction);

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
			// n????u co?? thanh ??????i tr??????ng gia?? ti????n job thi?? th????c hi????n trong If, n????u ko
			// thi?? ti????p tu??c update ca??c tr??????ng kha??c
			if (jobUpdate.getPaymentAmount() != null) {
				// check tra??ng tha??i thanh toa??n
				if (jobCurrent.getIsPaymentStatus() == 0 || jobCurrent.getIsPaymentStatus() == null) {
					// n????u ch??a thanh toa??n thi?? update theo y??u c????u va?? hi????n tra??ng tha??i ch??a
					// thanh toa??n ?????? user thanh toa??n sau
					jobCurrent.setPaymentAmount(jobUpdate.getPaymentAmount());
					// set la??i tra??n tha??i v????n ch??a thanh toa??n, tranh tr??????ng h????p bi?? null;
					jobCurrent.setIsPaymentStatus(0);
				} else {// n????u ??a?? thanh toa??n
					Double difference = jobCurrent.getPaymentAmount() - jobUpdate.getPaymentAmount();
					if (difference >= 0) {// n????u s???? ti????n ??a?? thanh toa??n l????n h??n ho????c b????ng s?? ti????n ??a?? mu????n
											// update
						// c????p nh????t s???? ti????n cu??a Job b????ng s???? ti????n truy????n va??o
						jobCurrent.setPaymentAmount(jobUpdate.getPaymentAmount());
						// c????ng th??m ti????n ch??nh l????ch cho User
						user.setBalance(user.getBalance() + difference);
						//Add transaction hoa??n ti????n th????a
						Transaction transaction = new Transaction();
						transaction.setPrice(difference);
						transaction.setContent("Job update refund!");
						transaction.setCreateAt(DateUtil.getTimeLongCurrent());
						transaction.setType(Transaction.TransactionType.REFUND);
						transaction.setJob_id(jobCurrent.getId());
						transaction.setUser_account_id(user.getId());
						transactionRepository.save(transaction);

						userRepository.save(user);

					} else { // n????u s???? ti????n ??a?? thanh toa??n be?? h??n s???? ti????n mu????n update
						// check s???? d??
						// n????u s???? d?? l????n be?? h??n ti????n ch??nh l????ch (nh??n -1 ?????? l????y s???? d????ng) thi??
						// kh??ng c????p nh????t th??ng tin n????a
						if (user.getBalance() < difference * (-1)) {
							return new ResponseObject(Constant.STATUS_ACTION_FAIL,
									"S???? d?? kh??ng ??u??, vui lo??ng ki????m tra va?? na??p th??m ti????n", result);
						}
						// tr??????ng h????p co??n la??i, s???? d?? l????n h??n ti????n ch??nh l????ch (nh??n -1 ?????? l????y
						// s???? d????ng)
						// c????p nh????t s???? ti????n mu????n update
						jobCurrent.setPaymentAmount(jobUpdate.getPaymentAmount());
						// tr???? ti????n trong ta??i khoa??n Use??
						user.setBalance(user.getBalance() - difference * (-1));
						//Add transaction thanh toa??n th??m ti????n job
						Transaction transaction = new Transaction();
						transaction.setPrice(difference * (-1));
						transaction.setContent("Update job create money!");
						transaction.setCreateAt(DateUtil.getTimeLongCurrent());
						transaction.setType(Transaction.TransactionType.PAYMENT);
						transaction.setJob_id(jobCurrent.getId());
						transaction.setUser_account_id(user.getId());
						transactionRepository.save(transaction);
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

			if (jobUpdate.getExpected_duration_id() != null) {
				jobCurrent.setExpected_duration_id(jobUpdate.getExpected_duration_id());
			}

			if (jobUpdate.getOtherSkills() != null || !jobUpdate.getOtherSkills().isEmpty()) {
				for (OtherSkill o: jobCurrent.getOtherSkills()
					 ) {
					otherSkillRepository.delete(o);
				}
				jobCurrent.setOtherSkills(null);
				for (OtherSkill o: jobUpdate.getOtherSkills()
					 ) {
					o.setJob_id(jobCurrent.getId());
					otherSkillRepository.save(o);
				}
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
			rl.setStatus(1);
			rl.setUpdateAt(DateUtil.getTimeLongCurrent());
				List<OtherSkill> skillJob = rl.getOtherSkills().stream().collect(Collectors.toList());
				ArrayList<Long> idSkillJob = new ArrayList<>();
				for (OtherSkill o: skillJob
				) {
					idSkillJob.add(o.getSkill_id());
				}

				Specification<UserFreelancer> specification = Specification.where(null);
				for (Long i : idSkillJob
				) {
					specification = specification.and(new FreelancerSpecification(new SearchCriteria("skill_id", "==skill", i)));
				}

				List<UserFreelancer> userFreelancers= userFreelancerRepository.findAll(specification);
				for (UserFreelancer u: userFreelancers
				) {
					JwtAuthServiceApp.listSendMail.add(new SendMailModel(u.getUser().getEmail(),"We found 1 job matching your skills", rl.getId().toString()));
				}




			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "Thanh toan thanh cong", jobRepository.save(rl));
		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, "Khong tim thay Job can thanh toan", null);
	}

	public ResponseObject getSuitableJob(String username) {
		User user = userRepository.findByUsername(username);
		List<Long> listSkill = new ArrayList<>();
		Specification<Job> specification = Specification.where(null);
		Specification<Job> specification1 = Specification.where(null);
		for (HasSkill hasSkill : user.getUserFreelancers().getHasSkills().stream().collect(Collectors.toList())) {
			listSkill.add(hasSkill.getSkill_id());
		}

		for (Long skillId : listSkill) {
			specification1 = specification.or(new JobSpecification(new SearchCriteria("skill_id", "==skill", skillId)));
		}
		specification = specification.and(specification1);
		List<Job> list = jobRepository.findAll(specification, PageRequest.of(0, 3,Sort.by("createAt").descending())).getContent();
//		if (listSkill.size() > 0) {
//			
//		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "success", list);
	}

}
