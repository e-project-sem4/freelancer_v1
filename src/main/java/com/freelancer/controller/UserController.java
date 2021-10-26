package com.freelancer.controller;

import javax.servlet.http.HttpServletRequest;

import com.freelancer.model.*;
import com.freelancer.search.JobSpecification;
import com.freelancer.search.SearchCriteria;
import com.freelancer.search.TransactionSpecification;
import com.freelancer.search.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelancer.security.JwtTokenProvider;
import com.freelancer.service.UserService;
import com.freelancer.utils.JWTService;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	JWTService jwtService;

	private static final String AUTHORIZATION = "Authorization";


	//get All/SEARCH
	@RequestMapping(value = "/searchList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseObject> searchList(
												 @RequestParam(value = "keySearch", required = false) Optional<String> keySearch
			, @RequestParam(value = "status", required = false) Optional<Integer> status
			, @RequestParam(value = "startAt", required = false) Optional<Long> startAt
			, @RequestParam(value = "endAt", required = false) Optional<Long> endAt
			, @RequestParam(value = "page", required = false) Optional<Integer> page
			, @RequestParam(value = "size", required = false) Optional<Integer> size
			, @RequestParam(value = "sort", required = false) Optional<Integer> sort) {
		Specification<User> specification = Specification.where(null);
		if (keySearch.isPresent() && !keySearch.get().isEmpty()) {
			specification = specification.and(new UserSpecification(new SearchCriteria("username", "like", keySearch.get())));
		}
		if (startAt.isPresent() && startAt.get() > 0) {
			specification = specification.and(new UserSpecification(new SearchCriteria("createAt", ">=", startAt.get())));
		}
		if (endAt.isPresent() && endAt.get() > 0) {
			specification = specification.and(new UserSpecification(new SearchCriteria("createAt", "<=", endAt.get())));
		}
		if (status.isPresent()&& status.get()>=0) {
			specification = specification.and(new UserSpecification(new SearchCriteria("status", "==", status.get())));
		}




		ResponseObject result = null;
		if (page.isPresent() && size.isPresent() && sort.isPresent()) {
			result = userService.searchList(specification, page.get(), size.get(), sort.get());
		}

		if (!page.isPresent() || !size.isPresent() || !sort.isPresent()) {
			result = userService.searchList(specification, 0, 0, 0);
		}


		return new ResponseEntity<>(result, HttpStatus.OK);
	}



	@RequestMapping(value = "/admin/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseObject> loginAdmin(@RequestParam String username, @RequestParam(value = "password") String password) {
		ResponseObject result = userService.signinAdmin(username, password);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}


	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseObject> login(@RequestParam String username, @RequestParam String password) {
		ResponseObject result = userService.signin(username, password);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseObject> getUserById(@PathVariable Long id) {
		ResponseObject result = userService.getUserById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/viewprofile", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseObject> viewProfile(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		String username = jwtTokenProvider.getUsername(token);
		ResponseObject result = userService.viewProfile(username);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/editprofile", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public ResponseEntity<ResponseObject> editProfile(@RequestBody User user, HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		String username = jwtTokenProvider.getUsername(token);
		user.setUsername(username);
		ResponseObject result = userService.editProfile(user);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/editprofilebusiness", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public ResponseEntity<ResponseObject> editBusiness(@RequestBody UserBusiness userBusiness,
			HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		String username = jwtTokenProvider.getUsername(token);
		ResponseObject result = userService.editBusiness(username, userBusiness);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/editprofilefreelancer", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public ResponseEntity<ResponseObject> editFreelancer(@RequestBody UserFreelancer userFreelancer,
			HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		String username = jwtTokenProvider.getUsername(token);
		ResponseObject result = userService.editFreelancer(username, userFreelancer);
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

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
		ResponseObject result = userService.delete(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	public ResponseEntity<ResponseObject> search(HttpServletRequest request, @RequestParam String keysearch,
			@PathVariable int page, @PathVariable int size) {
		String token = request.getHeader(AUTHORIZATION);
		String username = jwtTokenProvider.getUsername(token);
		ResponseObject result = userService.search(keysearch, username, page, size);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/refresh")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public String refresh(HttpServletRequest req) {
		return userService.refresh(req.getRemoteUser());
	}

}
