package com.freelancer.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelancer.model.ResponseObject;
import com.freelancer.model.User;
import com.freelancer.service.UserService;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseObject> login(@RequestParam String username, @RequestParam String password) {
		ResponseObject result = userService.signin(username, password);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseObject> getUserById(@PathVariable Long id) {
		ResponseObject result = userService.getUserById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/editprofile/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public ResponseEntity<ResponseObject> editProfile(@PathVariable Long id, HttpServletRequest request) {
		ResponseObject result = userService.editProfile(request, id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseObject> signup(@RequestBody User user) {
		ResponseObject result = userService.register(user);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseObject> changePassword(HttpServletRequest request) {
		ResponseObject result = userService.changePassword(request);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The user doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public String delete(@ApiParam("Username") @PathVariable String username) {
		userService.delete(username);
		return username;
	}

	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseObject> search(@RequestParam String username, @RequestParam String keysearch,
			@PathVariable int page, @PathVariable int size) {
		ResponseObject result = userService.search(keysearch, username, page, size);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/refresh")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public String refresh(HttpServletRequest req) {
		return userService.refresh(req.getRemoteUser());
	}

}
