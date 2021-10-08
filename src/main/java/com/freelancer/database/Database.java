package com.freelancer.database;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.freelancer.model.Complexity;
import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.Role;
import com.freelancer.model.Skill;
import com.freelancer.model.User;
import com.freelancer.repository.ComplexityRepository;
import com.freelancer.repository.ExpectedDurationRepository;
import com.freelancer.repository.SkillRepository;
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

	@Override
	public void run(String... args) throws Exception {
//		seedUserAccount();
//		seedComplexity();
//		seedExpected();
//		seedSkill();
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
			logger.info(userService.signup(new User(1L, "admin", "admin@gmail.com", "admin", "0987654321", "admin",
					new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)))));
			logger.info(userService.signup(new User(2L, "client", "client@gmail.com", "client", "0987654322", "client",
					new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)))));
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
}
