package com.freelancer;

import com.freelancer.sendmail.SendMailModel;
import com.freelancer.sendmail.ThreadSendMail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.freelancer.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SpringBootApplication
public class JwtAuthServiceApp {

	public static BlockingQueue<SendMailModel> listSendMail = new ArrayBlockingQueue<>(5000);
	@Autowired
	UserService userService;

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(JwtAuthServiceApp.class, args);
		ThreadSendMail threadSendMail = applicationContext.getBean(ThreadSendMail.class);
		threadSendMail.start();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
