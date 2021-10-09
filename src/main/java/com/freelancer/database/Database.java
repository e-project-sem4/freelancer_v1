package com.freelancer.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.freelancer.model.*;
import com.freelancer.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.freelancer.service.UserService;

//Create Docker
//docker run -d --name mysql-spring-boot-tutorial -e MYSQL_ALLOW_EMPTY_PASSWORD=true -e MYSQL_USER=root -e MYSQL_PASSWORD= -e MYSQL_DATABASE=spring_master -p 3309:3306 --volume mysql-spring-boot-tutorial-volume:/var/lib/mysql mysql:5.7.32
//mysql -h localhost -P 3309 --protocol=tcp -u root -p
//show databases;
//use tendatabase;
//show tables;
@Component
public class Database implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(Database.class);
	@Autowired
	ComplexityRepository complexityRepository;
	@Autowired
	UserService userService;
	@Autowired
	SkillRepository skillRepository;
	@Autowired
	ExpectedDurationRepository edRepository;
	@Autowired
	JobRepository jobRepository;
	@Autowired
	UserBusinessRepository business;
	@Autowired
	UserFreelancerRepository freelancer;
	@Override
	public void run(String... args) throws Exception {
		seedUserAccount();
		seedComplexity();
		seedExpected();
		seedSkill();
		seerUserBusiness();
		seedJob();
	}
	public Long date(){
		Date d = new Date();
		return d.getTime();
	}
	private void seedComplexity() {
		if (complexityRepository.count() == 0) {
			complexityRepository.save(new Complexity(1L, "Easy"));
			complexityRepository.save(new Complexity(2L, "Intermediate"));
			complexityRepository.save(new Complexity(3L, "Hard"));
		}
	}

	private void seedExpected() {
		if (edRepository.count() == 0) {
			edRepository.save(new ExpectedDuration(1L, "1 day"));
			edRepository.save(new ExpectedDuration(2L, "2-5 days"));
			edRepository.save(new ExpectedDuration(3L, "5-10 days"));
			edRepository.save(new ExpectedDuration(4L, "less than 1 month"));
			edRepository.save(new ExpectedDuration(5L, "1-3 months"));
			edRepository.save(new ExpectedDuration(6L, "3-6 months"));
			edRepository.save(new ExpectedDuration(7L, "6 or more months"));
		}
	}

	private void seedUserAccount() {
		if (userService.count() == 0) {
			userService.signup(new User(1L, "admin", "admin@gmail.com", "admin", "0987654321", "admin", new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN))));
			userService.signup(new User(2L, "client", "client@gmail.com", "client", "0987654322", "client", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(3L, "client3", "client3@gmail.com", "client", "0987654323", "client3", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(4L, "client4", "client4@gmail.com", "client", "0987654324", "client4", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(5L, "client5", "client5@gmail.com", "client", "0987654325", "client5", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(6L, "client6", "client6@gmail.com", "client", "0987654326", "client6", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(7L, "client7", "client7@gmail.com", "client", "0987654327", "client7", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(8L, "client8", "client8@gmail.com", "client", "0987654328", "client8", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(9L, "client9", "client9@gmail.com", "client", "0987654322", "client9", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(10L, "client10", "client10@gmail.com", "client", "0987654322", "client10", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(11L, "client11", "client11@gmail.com", "client", "0987654322", "client11", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(12L, "client12", "client12@gmail.com", "client", "0987654322", "client12", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(13L, "client13", "client13@gmail.com", "client", "0987654322", "client13", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(14L, "client14", "client14@gmail.com", "client", "0987654322", "client14", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
			userService.signup(new User(15L, "client15", "client15@gmail.com", "client", "0987654322", "client15", new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))));
		}
	}
	private void seerUserBusiness(){
		if (business.count() ==0){
			business.save(new UserBusiness(1L,2l,date(),"Cong ty Le Lê Hoàng Trình"));
			business.save(new UserBusiness(2L,3l,date(),"Cong ty Nguyễn Xuân Phúc"));
			business.save(new UserBusiness(3L,4l,date(),"Cong ty Lý Lê Trọng Phú"));
			business.save(new UserBusiness(4L,5l,date(),"Cong ty Phạm Văn Hòa"));
			business.save(new UserBusiness(5L,6l,date(),"Cong ty Nguyễn Xuân Lộc"));
			business.save(new UserBusiness(6L,7l,date(),"Cong ty Nguyễn Thành Dương"));
			business.save(new UserBusiness(7L,8l,date(),"Cong ty HoangLong"));
			business.save(new UserBusiness(8L,9l,date(),"Cong ty Hung Nguyen"));
			business.save(new UserBusiness(9L,10l,date(),"Cong ty Thanh Phat"));
			business.save(new UserBusiness(10L,11l,date(),"Cong ty Ha Noi"));
		}
	}


	private void seedSkill() {
		if (skillRepository.count() == 0) {
			skillRepository.save(new Skill(1L, "JavaScript"));
			skillRepository.save(new Skill(2L, "Python"));
			skillRepository.save(new Skill(3L, "C/C++"));
			skillRepository.save(new Skill(4L, "Java"));
			skillRepository.save(new Skill(5L, "PHP"));
			skillRepository.save(new Skill(6L, "Swift"));
			skillRepository.save(new Skill(7L, "C# (C-Sharp)"));
			skillRepository.save(new Skill(8L, "Ruby"));
			skillRepository.save(new Skill(9L, "Objective-C"));
			skillRepository.save(new Skill(10L, "SQL"));
		}
	}
	private void seedJob(){
		if (jobRepository.count() == 0){
			jobRepository.save((new Job(1L, 2L, 1L,1L,1L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(2L, 2L, 2L,2L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(3L, 2L, 3L,3L,3L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(4L, 3L, 1L,1L,1L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(5L, 3L, 2L,3L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(6L, 4L, 2L,3L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(7L, 4L, 3L,3L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(8L, 5L, 3L,3L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(9L, 5L, 2L,3L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(10L, 6L, 1L,1L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(11L, 6L, 2L,2L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(12L, 7L, 3L,3L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(13L, 7L, 2L,4L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(14L, 8L, 1L,5L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(15L, 9L, 2L,6L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(16L, 9L, 3L,1L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(17L, 10L, 1L,4L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(18L, 10L, 2L,3L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));
			jobRepository.save((new Job(19L, 10L, 3L,3L,2L, "I'm creating youtube videos but would like make it as automated as possible If you can do it, plz bid on this project. will discuss more detail via chat. Thank you.","I need a program that will create videos for a specific template",999999999)));


		}
	}
}
