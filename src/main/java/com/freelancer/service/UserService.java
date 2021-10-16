package com.freelancer.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freelancer.dto.ResponseProfileUserDto;
import com.freelancer.exception.CustomException;
import com.freelancer.model.Job;
import com.freelancer.model.Proposal;
import com.freelancer.model.ResponseObject;
import com.freelancer.model.Role;
import com.freelancer.model.User;
import com.freelancer.model.UserBusiness;
import com.freelancer.model.UserFreelancer;
import com.freelancer.repository.JobRepository;
import com.freelancer.repository.UserBusinessRepository;
import com.freelancer.repository.UserFreelancerRepository;
import com.freelancer.repository.UserRepository;
import com.freelancer.security.JwtTokenProvider;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class UserService {

	Logger logger = ConfigLog.getLogger(UserService.class);

	Gson gson = new Gson();

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

	public ResponseObject signin(String username, String password) {
		String message;
		try {
			logger.info("login nè");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			User user = userRepository.findByUsername(username);
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
		User user = null;
		if (optionalUser.isPresent()) {
			message = "success";
			user = optionalUser.get();
			user.setPassword(null);
			logger.info("get user success");
			UserBusiness business = user.getUserBusinesses();
			List<Job> listJob = jobRepository.findAllByUser_business_id(business.getId());
			for (Job j : listJob) {
				j.setUserBusiness(null);
			}
			business.setListJob(listJob);
			UserFreelancer freelancer = user.getUserFreelancers();

			for (Proposal p : freelancer.getProposals()) {
				p.setJobName(jobRepository.findById(p.getJob_id()).get().getName());
			}
			profileUserDto = new ResponseProfileUserDto(user, business, freelancer);
		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, profileUserDto);
	}

	public ResponseObject getUserById(Long id) {
		logger.info("call to get user by id: " + id);
		ResponseProfileUserDto profileUserDto = null;
		Optional<User> optionalUser = userRepository.findById(id);
		String message = "can not find user";
		User user = null;
		if (optionalUser.isPresent()) {
			message = "success";
			user = optionalUser.get();
			user.setPhone(null);
			user.setEmail(null);
			user.setRoles(null);
			user.getUserBusinesses().setLocation(null);
			user.getUserFreelancers().setLocation(null);
			user.setPassword(null);
			logger.info("get user success");
			UserBusiness business = user.getUserBusinesses();
			List<Job> listJob = jobRepository.findAllByUser_business_id(business.getId());
			for (Job j : listJob) {
				j.setUserBusiness(null);
			}
			business.setListJob(listJob);
			UserFreelancer freelancer = user.getUserFreelancers();

			for (Proposal p : freelancer.getProposals()) {
				p.setJobName(jobRepository.findById(p.getJob_id()).get().getName());
			}
			profileUserDto = new ResponseProfileUserDto(user, business, freelancer);
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
			if(user.getUserFreelancers() != null) {
				userBusiness.setId(user.getUserBusinesses().getId());
			}
			UserBusiness result = userBusinessRepository.save(userBusiness);
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "success", result);
		} catch (Exception e) {
			return new ResponseObject(Constant.STATUS_ACTION_FAIL, "Fail to edit business", null);
		}
	}

	public ResponseObject editFreelancer(String userCreate, UserFreelancer userFreelancer) {
		try {
			logger.info("call to edit user freelancer" + userFreelancer.toString());
			userFreelancer.setUpdateAt(System.currentTimeMillis());
			User user = userRepository.findByUsername(userCreate);
			userFreelancer.setUser_account_id(user.getId());
			if(user.getUserFreelancers() != null) {
				userFreelancer.setId(user.getUserFreelancers().getId());
			}
			UserFreelancer result = userFreelancerRepository.save(userFreelancer);
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
		if (page > 0 && size > 0 && (sort > 4 || sort < 1)) {
			list = userFreelancerRepository.findAll(specification, PageRequest.of(page - 1, size)).getContent();
		} else if (page > 0 && size > 0 && sort == 1) {
			list = userFreelancerRepository
					.findAll(specification, PageRequest.of(page - 1, size, Sort.by("createAt").descending()))
					.getContent();
		} else if (page > 0 && size > 0 && sort == 2) {
			list = userFreelancerRepository.findAll(specification, PageRequest.of(page - 1, size, Sort.by("createAt").ascending()))
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
}
