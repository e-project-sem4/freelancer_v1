package com.freelancer.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.freelancer.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freelancer.JwtAuthServiceApp;
import com.freelancer.dto.ResponseProfileUserDto;
import com.freelancer.exception.CustomException;
import com.freelancer.repository.ChatKeyUserRepository;
import com.freelancer.repository.HasSkillRepository;
import com.freelancer.repository.JobRepository;
import com.freelancer.repository.TransactionRepository;
import com.freelancer.repository.UserBusinessRepository;
import com.freelancer.repository.UserFreelancerRepository;
import com.freelancer.repository.UserRepository;
import com.freelancer.security.JwtTokenProvider;
import com.freelancer.sendmail.SendMailModel;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.freelancer.utils.DateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class UserService {

	Logger logger = ConfigLog.getLogger(UserService.class);

	Gson gson = new Gson();

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserBusinessRepository userBusinessRepository;

	@Autowired
	private UserFreelancerRepository userFreelancerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ChatKeyUserRepository chatKeyUserRepository;

	@Autowired
	private HasSkillRepository hasSkillRepository;

	public ResponseObject signinAdmin(String username, String password) {
		String message = "Login fail";
		try {
			logger.info("login n??");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			User user = userRepository.findByUsername(username);
			String token = jwtTokenProvider.createToken(username, user.getRoles());
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			if (!authentication.getAuthorities().iterator().next().toString().equals("ROLE_ADMIN")) {
				message = "username or password wrong";
				return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
			}
			message = token;
			user.setPassword(null);
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, user);
		} catch (AuthenticationException e) {
			message = "username or password wrong";
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}
	}

	public ResponseObject signin(String username, String password) {
		String message;
		try {
			logger.info("login n??");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			User user = userRepository.findByUsername(username);
			UserFreelancer freelancer = userFreelancerRepository.getFreelancerByUserAccountId(user.getId());
			UserBusiness business = userBusinessRepository.getBusinessByUserAccountId(user.getId());
			List<ChatKeyUser> chatKeyUsers = new ArrayList<>();
			if (freelancer != null && business != null) {
				chatKeyUsers = chatKeyUserRepository.findChatKey(freelancer.getId(), business.getId());
			}
			user.setChatKeyUsers(chatKeyUsers);
			user.setPassword(null);
			String token = jwtTokenProvider.createToken(username, user.getRoles());
			message = token;
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, user);
		} catch (AuthenticationException e) {
			message = "username or password wrong";
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}
	}

	public String signup(User user) {
		if (!userRepository.existsByUsername(user.getUsername())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setStatus(1);
			user.setCreateAt(System.currentTimeMillis());
			userRepository.save(user);
			return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		} else {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public void delete(String username) {
		userRepository.deleteByUsername(username);
	}

//	public User search(String username) {
//		User user = userRepository.findByUsername(username);
//		if (user == null) {
//			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
//		}
//		return user;
//	}

	public ResponseObject search(String keysearch, String username, int page, int size) {
		logger.info("call to search user with keysearch: " + keysearch);
		String message = "success";
		List<User> list = userRepository.searchUser(keysearch, username, PageRequest.of(page - 1, size));
		Long total = userRepository.countUser(keysearch, username);
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
	}

	public User whoami(HttpServletRequest req) {
		return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	public String refresh(String username) {
		return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
	}

	public long count() {
		return userRepository.count();
	}

	public ResponseObject viewProfile(String username) {
		logger.info("call to view profile with username: " + username);
		ResponseProfileUserDto profileUserDto = null;
		Optional<User> optionalUser = userRepository.findById(userRepository.findByUsername(username).getId());
		String message = "can not find user";
		UserBusiness business;
		UserFreelancer freelancer = null;
		List<ChatKeyUser> chatKeyUsers = null;
		User user = null;
		if (optionalUser.isPresent()) {
			message = "success";
			user = optionalUser.get();
			user.setPassword(null);
			logger.info("get user success");
			business = user.getUserBusinesses();
			if (business != null) {
				List<Job> listJob = jobRepository.findAllByUser_business_id(business.getId());
				for (Job j : listJob) {
					j.setUserBusiness(null);
				}
				business.setListJob(listJob);
				freelancer = user.getUserFreelancers();

				if (freelancer != null) {
					for (Proposal p : freelancer.getProposals()) {
						p.setJobName(jobRepository.findById(p.getJob_id()).get().getName());
					}
					chatKeyUsers = chatKeyUserRepository.findChatKey(freelancer.getId(),
							business.getId());
				}
			}
			profileUserDto = new ResponseProfileUserDto(user, business, freelancer, chatKeyUsers);
		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, profileUserDto);
	}

	public ResponseObject getUserById(Long id) {
		logger.info("call to get user by id: " + id);
		ResponseProfileUserDto profileUserDto = null;
		Optional<User> optionalUser = userRepository.findById(id);
		String message = "can not find user";
		User user = null;
		UserBusiness business;
		UserFreelancer freelancer = null;
		if (optionalUser.isPresent()) {
			message = "success";
			user = optionalUser.get();
			user.setPhone(null);
			user.setEmail(null);
			user.setRoles(null);
			user.setPassword(null);
			logger.info("get user success");
			business = user.getUserBusinesses();
			if (business != null) {
				List<Job> listJob = jobRepository.findAllByUser_business_id(business.getId());
				for (Job j : listJob) {
					j.setUserBusiness(null);
				}
				business.setListJob(listJob);
				freelancer = user.getUserFreelancers();
				if (freelancer != null) {
					for (Proposal p : freelancer.getProposals()) {
						p.setJobName(jobRepository.findById(p.getJob_id()).get().getName());
					}
				}
			}
			profileUserDto = new ResponseProfileUserDto(user, business, freelancer, null);
		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, profileUserDto);
	}

	public ResponseObject editProfile(User user) {
		try {
			logger.info("call to edit user" + user.toString());
			User returnUser = userRepository.findByUsername(user.getUsername());
			if (user.getEmail() != null && !user.getEmail().isEmpty())
				returnUser.setEmail(user.getEmail());
			if (user.getPhone() != null && !user.getPhone().isEmpty())
				returnUser.setPhone(user.getPhone());
			if (user.getPhone() != null && !user.getPhone().isEmpty())
				returnUser.setFullName(user.getFullName());
			if (user.getThumbnail() != null && !user.getThumbnail().isEmpty())
				returnUser.setThumbnail(user.getThumbnail());
			user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
			user.setUpdateAt(System.currentTimeMillis());
			User result = userRepository.save(returnUser);
			result.setPassword(null);
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "success", result);
		} catch (Exception e) {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, "Fail to edit profile", null);
		}
	}

	public ResponseObject editBusiness(String userCreate, UserBusiness userBusiness) {
		try {
			logger.info("call to edit user business" + userBusiness.toString());
			userBusiness.setUpdateAt(System.currentTimeMillis());
			User user = userRepository.findByUsername(userCreate);
			userBusiness.setUser_account_id(user.getId());
			if (user.getUserBusinesses() != null) {
				userBusiness.setId(user.getUserBusinesses().getId());
				userBusiness.setAverageGrade(user.getUserBusinesses().getAverageGrade());
			} else {
				userBusiness.setAverageGrade(0);
			}
			UserBusiness result = userBusinessRepository.save(userBusiness);
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "success", result);
		} catch (Exception e) {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, "Fail to edit business", null);
		}
	}

	@Transactional
	public ResponseObject editFreelancer(String userCreate, UserFreelancer userFreelancer) {
		try {
			logger.info("call to edit user freelancer" + userFreelancer.toString());
			userFreelancer.setUpdateAt(System.currentTimeMillis());
			User user = userRepository.findByUsername(userCreate);
			userFreelancer.setUser_account_id(user.getId());
			UserFreelancer result = null;
			if (user.getUserFreelancers() != null) {
				userFreelancer.setId(user.getUserFreelancers().getId());
				userFreelancer.setStatusSearchJob(user.getUserFreelancers().getStatusSearchJob());
				userFreelancer.setAverageGrade(user.getUserFreelancers().getAverageGrade());
				hasSkillRepository.deleteSkills(user.getUserFreelancers().getId());
			} else {
				userFreelancer.setStatusSearchJob(1);
				userFreelancer.setAverageGrade(0);
			}
			if (userFreelancer.getHasSkills().size() > 0) {
				result = userFreelancerRepository.save(userFreelancer);
				for (HasSkill item : userFreelancer.getHasSkills()) {
					item.setUser_freelancer_id(result.getId());
				}
				hasSkillRepository.saveAll(userFreelancer.getHasSkills());
				userFreelancer.setHasSkills(null);
			}

			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "success", result);
		} catch (Exception e) {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, "Fail to edit freelancer", null);
		}
	}

	public ResponseObject register(User user) {
		user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
		logger.info("call to register" + user.toString());
		user.setCreateAt(System.currentTimeMillis());
		user.setStatus(1);
		String message = "success";
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User result = userRepository.save(user);
		logger.info("register user: " + result);
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
	}

	@Transactional
	public ResponseObject changePassword(HttpServletRequest request) {
		try {
			JsonObject jsonString = JsonParser.parseReader(request.getReader()).getAsJsonObject();
			logger.info("call to change password" + jsonString);
			if (jsonString.get("oldPassword") == null) {
				logger.info("old password null");
				return new ResponseObject(Constant.STATUS_ACTION_FAIL, "Old password is invalid", null);
			} else if (jsonString.get("newPassword") == null) {
				logger.info("new password null");
				return new ResponseObject(Constant.STATUS_ACTION_FAIL, "New password is invalid", null);
			} else if (jsonString.get("username") == null) {
				logger.info("username null");
				return new ResponseObject(Constant.STATUS_ACTION_FAIL, "Username is invalid", null);
			} else {
				String oldPassword = jsonString.get("oldPassword").getAsString();
				String username = jsonString.get("username").getAsString();
				User user = userRepository.findByUsername(username);
				if (user != null) {
					if (passwordEncoder.matches(oldPassword, user.getPassword())) {
						String newPassword = jsonString.get("newPassword").getAsString();
						userRepository.changePasswordByUsername(passwordEncoder.encode(newPassword), username);
						logger.info("Change password success");
						return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "Change password success", null);
					} else {
						logger.info("old password wrong");
						return new ResponseObject(Constant.STATUS_ACTION_FAIL, "Old password is wrong", null);
					}
				}
				logger.info("coult not find user with: " + username);
			}
		} catch (Exception e) {
			logger.error("error change password", e);
		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, "Change password fail", null);
	}

	public ResponseObject delete(Long id) {
		try {
			logger.info("call to delete user by id: " + id);
			userRepository.deleteById(id);
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "success", null);
		} catch (Exception e) {
			logger.info("error delete user by id", e);
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, "Fail to edit freelancer", null);
		}
	}

	public ResponseObject searchFreelancer(Specification<UserFreelancer> specification, int page, int size, int sort) {
		List<UserFreelancer> list = null;
		if (page > 0 && size > 0 && (sort > 2 || sort < 1)) {
			list = userFreelancerRepository.findAll(specification, PageRequest.of(page - 1, size)).getContent();
		} else if (page > 0 && size > 0 && sort == 1) {
			list = userFreelancerRepository
					.findAll(specification, PageRequest.of(page - 1, size, Sort.by("averageGrade").descending()))
					.getContent();
		} else if (page > 0 && size > 0 && sort == 2) {
			list = userFreelancerRepository
					.findAll(specification, PageRequest.of(page - 1, size, Sort.by("averageGrade").ascending()))
					.getContent();
		} else if (page == 0 && size == 0 && sort == 0) {
			list = userFreelancerRepository.findAll(specification);
		}
		for (UserFreelancer j : list) {
			j.setProposals(null);
			j.getUser().setPassword(null);
			j.getUser().setPhone(null);
			j.getUser().setEmail(null);
			j.getUser().setUsername(null);
			j.getUser().setBalance(null);
		}
		Long total = Long.valueOf(userFreelancerRepository.findAll(specification).size());
		String message = "success";
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
	}

	public ResponseObject changeStatusFreelancer(String username) {
		String message = "not success";
		User user = userRepository.findByUsername(username);
		Long currentFreelancerId = user.getUserFreelancers().getId();
		if (currentFreelancerId != null) {
			UserFreelancer userFreelancer = userFreelancerRepository.getOne(currentFreelancerId);
			if (userFreelancer.getStatusSearchJob() == 0) {
				userFreelancer.setStatusSearchJob(1);
			} else {
				userFreelancer.setStatusSearchJob(0);
			}
			UserFreelancer result = userFreelancerRepository.save(userFreelancer);
			message = "success";
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
	}

	public ResponseObject searchList(Specification<User> specification, int page, int size, int sort) {
		List<User> list = null;
		if (page > 0 && size > 0 && (sort > 4 || sort < 1)) {
			list = userRepository.findAll(specification, PageRequest.of(page - 1, size)).getContent();
		} else if (page > 0 && size > 0 && sort == 1) {
			list = userRepository
					.findAll(specification, PageRequest.of(page - 1, size, Sort.by("createAt").descending()))
					.getContent();
		} else if (page > 0 && size > 0 && sort == 2) {
			list = userRepository
					.findAll(specification, PageRequest.of(page - 1, size, Sort.by("createAt").ascending()))
					.getContent();
		} else if (page > 0 && size > 0 && sort == 3) {
			list = userRepository
					.findAll(specification, PageRequest.of(page - 1, size, Sort.by("balance").descending()))
					.getContent();
		} else if (page > 0 && size > 0 && sort == 4) {
			list = userRepository.findAll(specification, PageRequest.of(page - 1, size, Sort.by("balance").ascending()))
					.getContent();
		} else if (page == 0 && size == 0 && sort == 0) {
			list = userRepository.findAll(specification);
		}

		Long total = Long.valueOf(userRepository.findAll(specification).size());
		String message = "success";
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
	}

	// Rut tien
	public ResponseObject withdrawCash(String username, Double amount) {
		String message = "Insufficient account balance";
		User user = userRepository.findByUsername(username);
		if (user.getBalance() >= amount) {
			user.setBalance(user.getBalance() - amount);
			message = "Success";
			User result = userRepository.save(user);

			// Transaction
			Transaction transaction = new Transaction();
			transaction.setUser_account_id(user.getId());
			transaction.setPrice(amount);
			transaction.setContent("Withdraw Cash");
			transaction.setCreateAt(DateUtil.getTimeLongCurrent());
			transaction.setType(Transaction.TransactionType.WITHDRAW);
			transactionRepository.save(transaction);

			// Send mail

			String email = user.getEmail();
			JwtAuthServiceApp.listSendMail.add(new SendMailModel(email,
					"Congratulations, you have successfully withdrawn " + amount + "$.Thank you for using our service!",
					"2"));

			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
	}

	// Na??p tien
	public ResponseObject recharge(String username, Double amount, String oderId) {
		String message = "Insufficient account balance";
		User user = userRepository.findByUsername(username);
		if (user != null) {
			user.setBalance(user.getBalance() + amount);
			message = "Success";
			User result = userRepository.save(user);

			// Transaction
			Transaction transaction = new Transaction();
			transaction.setUser_account_id(user.getId());
			transaction.setPrice(amount);
			transaction.setOrderID(oderId);
			transaction.setContent("Recharge Cash");
			transaction.setCreateAt(DateUtil.getTimeLongCurrent());
			transaction.setType(Transaction.TransactionType.RECHARGE);
			transactionRepository.save(transaction);
			// Send mail
			String email = user.getEmail();
			JwtAuthServiceApp.listSendMail.add(new SendMailModel(email,
					"Congratulations, you have successfully recharge " + amount + "$.Thank you for using our service!",
					"2"));

			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
		}
		return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
	}

	public ResponseObject inviteEmail(String username, Long userId, Long jobId) {
		String fullNameOwner = userRepository.findByUsername(username).getFullName();
		String email = userRepository.findById(userId).get().getEmail();

		// send mail
		JwtAuthServiceApp.listSendMail.add(new SendMailModel(email,
				"Congratulations you have received a job offer from " + fullNameOwner + ". Good luck!!!",
				jobId.toString()));
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "Sent!", null);
	}
	public ResponseObject getAccount(Long id) {
		logger.info("call to get user by id: " + id);
		ResponseProfileUserDto profileUserDto = null;
		Optional<User> optionalUser = userRepository.findById(id);
		String message = "can not find user";
		User user = null;
		UserBusiness business;
		UserFreelancer freelancer = null;
		if (optionalUser.isPresent()) {
			message = "success";
			user = optionalUser.get();
			user.setPassword(null);
			logger.info("get user success");
			business = user.getUserBusinesses();
			if (business != null) {
				List<Job> listJob = jobRepository.findAllByUser_business_id(business.getId());
				for (Job j : listJob) {
					j.setUserBusiness(null);
				}
				business.setListJob(listJob);
				freelancer = user.getUserFreelancers();
				if (freelancer != null) {
					for (Proposal p : freelancer.getProposals()) {
						p.setJobName(jobRepository.findById(p.getJob_id()).get().getName());
					}
				}
			}
			profileUserDto = new ResponseProfileUserDto(user, business, freelancer, null);
		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, profileUserDto);
	}


	public ResponseObject updateProfile(User obj, Long id) {
		logger.info("call to get obj to update by id: " + id);
		User obj1 = userRepository.getOne(id);
		String message = "can not find obj";

		if (obj1.getId() != null) {
			if (obj.getFullName() != null && !obj.getFullName().isEmpty()) {
				obj1.setFullName(obj.getFullName());
			}
			if (obj.getThumbnail() != null && !obj.getThumbnail().isEmpty()) {
				obj1.setThumbnail(obj.getThumbnail());
			}
			if (obj.getEmail() != null && !obj.getEmail().isEmpty()) {
				obj1.setEmail(obj.getEmail());
			}
			if (obj.getBalance() != null ) {
				obj1.setBalance(obj.getBalance());
			}
			if (obj.getStatus() != null) {
				obj1.setStatus(obj.getStatus());
			}
			obj1.setUpdateAt(DateUtil.getTimeLongCurrent());
			 User result = userRepository.save(obj1);
			message = "update success";
			logger.info("update obj success");
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
		} else {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
		}
	}
}
