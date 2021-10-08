package com.freelancer.service;

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

import com.freelancer.exception.CustomException;
import com.freelancer.model.ResponseObject;
import com.freelancer.model.User;
import com.freelancer.repository.UserRepository;
import com.freelancer.security.JwtTokenProvider;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.google.gson.Gson;

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

	public String signin(String username, String password) {
		try {
			logger.info("login nè");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
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

	public ResponseObject search(String keysearch, int page, int size) {
		logger.info("call to search user with keysearch: " + keysearch);
		String message = "success";
		List<User> list = userRepository.searchUser(keysearch, 1L, PageRequest.of(page - 1, size));
		Long total = userRepository.countUser(keysearch, 1L);
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

	public ResponseObject createUser(User user) {
		logger.info("call to create user" + user.toString());
		String message = "success";
		User result = userRepository.save(user);
		logger.info("create user: " + result);
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
	}
}
