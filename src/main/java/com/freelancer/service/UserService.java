package com.freelancer.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freelancer.exception.CustomException;
import com.freelancer.model.ResponseObject;
import com.freelancer.model.Role;
import com.freelancer.model.User;
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
	private UserRepository userRepository;

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
			return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, null);
		}
	}

	public String signup(User user) {
		if (!userRepository.existsByUsername(user.getUsername())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
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

	public ResponseObject getUserById(Long id) {
		logger.info("call to get user by id: " + id);
		Optional<User> optionalUser = userRepository.findById(id);
		String message = "can not find user";
		User user = null;
		if (optionalUser.isPresent()) {
			message = "success";
			user = optionalUser.get();
			user.setPassword(null);
			logger.info("get user success");
		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, user);
	}

	public ResponseObject editProfile(HttpServletRequest request, Long idUser) {
		try {
			JsonObject jsonString = JsonParser.parseReader(request.getReader()).getAsJsonObject();
			logger.info("call to edit user by id: " + idUser);
			logger.info("jsonString: " + jsonString);
		} catch (Exception e) {
		}
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "", null);
	}

	public ResponseObject register(User user) {
		user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
		logger.info("call to register" + user.toString());
		String message = "success";
		User result = userRepository.save(user);
		logger.info("create user: " + result);
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
}
