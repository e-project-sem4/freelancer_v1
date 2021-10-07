package com.freelancer.database;

import com.freelancer.model.Role;
import com.freelancer.model.User;
import com.freelancer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;

//Create Docker
//docker run -d --name mysql-spring-boot-tutorial -e MYSQL_ALLOW_EMPTY_PASSWORD=true -e MYSQL_USER=root -e MYSQL_PASSWORD= -e MYSQL_DATABASE=spring_master -p 3309:3306 --volume mysql-spring-boot-tutorial-volume:/var/lib/mysql mysql:5.7.32
//mysql -h localhost -P 3309 --protocol=tcp -u root -p
//show databases;
//use tendatabase;
//show tables;
@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    @Bean
    CommandLineRunner initDatabase(UserService userService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                User user1 = new User();
//                user1.setUsername("admin");
//                user1.setPassword("admin");
//                user1.setPhone("09123213");
//                user1.setEmail("admin@gmail.com");
//                user1.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
//
//                User user2 = new User();
//                user2.setUsername("client");
//                user2.setPassword("client");
//                user2.setPhone("09123213");
//                user2.setEmail("client@gmail.com");
//                user2.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
//
//                logger.info("insert data " + userService.signup(user1));
//                logger.info("insert data " + userService.signup(user2));
            }
        };
    }
}
