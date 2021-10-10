package com.freelancer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.freelancer.service.UserService;

@SpringBootApplication
public class JwtAuthServiceApp {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthServiceApp.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
