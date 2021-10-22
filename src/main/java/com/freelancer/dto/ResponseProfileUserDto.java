package com.freelancer.dto;

import java.util.List;

import com.freelancer.model.ChatKeyUser;
import com.freelancer.model.User;
import com.freelancer.model.UserBusiness;
import com.freelancer.model.UserFreelancer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProfileUserDto {

	private User user;
	private UserBusiness business;
	private UserFreelancer freelancer;
	private List<ChatKeyUser> chatKeyUsers;
}
