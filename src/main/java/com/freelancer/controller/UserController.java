package com.freelancer.controller;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelancer.dto.UserDataDTO;
import com.freelancer.dto.UserResponseDTO;
import com.freelancer.model.User;
import com.freelancer.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/login")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 422, message = "Invalid username/password supplied") })
	public String login(@RequestBody User user) {
		return userService.signin(user.getUsername(), user.getPassword());
	}

	@PostMapping("/signup")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use") })
	public String signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
		return userService.signup(modelMapper.map(user, User.class));
	}

	@DeleteMapping(value = "/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation(value = "${UserController.delete}", authorizations = { @Authorization(value = "apiKey") })
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The user doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public String delete(@ApiParam("Username") @PathVariable String username) {
		userService.delete(username);
		return username;
	}

	@GetMapping(value = "/me")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class, authorizations = {
			@Authorization(value = "apiKey") })
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public UserResponseDTO whoami(HttpServletRequest req) {
		return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
	}
	
	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> search(@RequestParam String keysearch, @PathVariable int page,
			@PathVariable int size) {
		HttpStatus httpStatus = HttpStatus.OK;
		String result = userService.search(keysearch, page, size);
		if (result == null) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(result, httpStatus);
	}

	@GetMapping("/refresh")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public String refresh(HttpServletRequest req) {
		return userService.refresh(req.getRemoteUser());
	}

}
