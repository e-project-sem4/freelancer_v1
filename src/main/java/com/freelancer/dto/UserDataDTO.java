package com.freelancer.dto;

import java.util.List;

import com.freelancer.model.Role;
import com.freelancer.model.User;
import com.freelancer.model.UserBusiness;
import com.freelancer.model.UserFreelancer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO {

	private String id;
	private String username;
	private String email;
	private String password;
	private String phone;
	private String fullName;
	private List<Role> roles;
	private UserFreelancer userFreelancers;
	private UserBusiness userBusiness;

	public UserDataDTO mappingUser(User user) {
		this.id = user.getId().toString();
		this.username = user.getUsername();
		this.fullName = user.getFullName();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		this.roles = user.getRoles();
		this.userFreelancers = user.getUserFreelancers();
		this.userFreelancers.setUser(null);
		this.userBusiness = user.getUserBusinesses();
		this.userBusiness.setUser(null);
		return this;
	}

}
