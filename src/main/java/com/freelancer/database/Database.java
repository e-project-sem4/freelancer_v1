package com.freelancer.database;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.freelancer.model.Complexity;
import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.HasSkill;
import com.freelancer.model.Job;
import com.freelancer.model.OtherSkill;
import com.freelancer.model.Proposal;
import com.freelancer.model.ProposalStatusCatalog;
import com.freelancer.model.Role;
import com.freelancer.model.Skill;
import com.freelancer.model.User;
import com.freelancer.model.UserBusiness;
import com.freelancer.model.UserFreelancer;
import com.freelancer.repository.ComplexityRepository;
import com.freelancer.repository.ExpectedDurationRepository;
import com.freelancer.repository.HasSkillRepository;
import com.freelancer.repository.JobRepository;
import com.freelancer.repository.OtherSkillRepository;
import com.freelancer.repository.ProposalRepository;
import com.freelancer.repository.ProposalStatusCatalogRepository;
import com.freelancer.repository.SkillRepository;
import com.freelancer.repository.UserBusinessRepository;
import com.freelancer.repository.UserFreelancerRepository;
import com.freelancer.service.UserService;
import com.freelancer.utils.DateUtil;

//Create Docker
//docker run -d --name mysql-spring-boot-tutorial -e MYSQL_ALLOW_EMPTY_PASSWORD=true -e MYSQL_USER=root -e MYSQL_PASSWORD= -e MYSQL_DATABASE=spring_master -p 3309:3306 --volume mysql-spring-boot-tutorial-volume:/var/lib/mysql mysql:5.7.32
//mysql -h localhost -P 3309 --protocol=tcp -u root -p
//show databases;
//use tendatabase;
//show tables;
@Component
public class Database implements CommandLineRunner {
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
    @Autowired
    OtherSkillRepository otherSkillRepository;
    @Autowired
    HasSkillRepository hasSkillRepository;
    @Autowired
    ProposalStatusCatalogRepository proposalStatus;
    @Autowired
    ProposalRepository proposal;
    @Override
    public void run(String... args) throws Exception {
        if (userService.count() == 0) {
            seedUserAccount();
            seedComplexity();
            seedExpected();
            seedSkill();
            seedUserBusiness();
            seedUserFreelancer();
            seedJob();
            seedOtherSkill();
            seedHasSkill();
            seedProposalStatus();
            seedProposal();
        }
    }

    //    public Long DateUtil.getTimeLongCurrent() {
//        Date d = new DateUtil.getTimeLongCurrent();
//        return d.getTime();
//    }
    private void seedComplexity() {
        complexityRepository.save(new Complexity(1L, "Easy",1));
        complexityRepository.save(new Complexity(2L, "Intermediate",1));
        complexityRepository.save(new Complexity(3L, "Hard",1));

    }

    private void seedExpected() {
        edRepository.save(new ExpectedDuration(1L, "1 day",1));
        edRepository.save(new ExpectedDuration(2L, "2-5 days",1));
        edRepository.save(new ExpectedDuration(3L, "5-10 days",1));
        edRepository.save(new ExpectedDuration(4L, "less than 1 month",1));
        edRepository.save(new ExpectedDuration(5L, "1-3 months",1));
        edRepository.save(new ExpectedDuration(6L, "3-6 months",1));
        edRepository.save(new ExpectedDuration(7L, "6 or more months",1));
    }

    private void seedUserAccount() {

        userService.signup(new User(1L, "admin", "admin@gmail.com", "admin", "0987654321", "admin",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)), 2344.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(2L, "client", "client@gmail.com", "client", "0987654322", "client",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 2344.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(3L, "client3", "client3@gmail.com", "client", "0987654323", "client3",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 34.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(4L, "client4", "client4@gmail.com", "client", "0987654324", "client4",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 13.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(5L, "client5", "client5@gmail.com", "client", "0987654325", "client5",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 200.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(6L, "client6", "client6@gmail.com", "client", "0987654326", "client6",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 12.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(7L, "client7", "client7@gmail.com", "client", "0987654327", "client7",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 44.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(8L, "client8", "client8@gmail.com", "client", "0987654328", "client8",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 44.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(9L, "client9", "client9@gmail.com", "client", "0987654322", "client9",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(10L, "client10", "client10@gmail.com", "client", "0987654322", "client10",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(11L, "client11", "client11@gmail.com", "client", "0987654322", "client11",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(12L, "client12", "client12@gmail.com", "client", "0987654322", "client12",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(13L, "client13", "client13@gmail.com", "client", "0987654322", "client13",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(14L, "client14", "client14@gmail.com", "client", "0987654322", "client14",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));
        userService.signup(new User(15L, "client15", "client15@gmail.com", "client", "0987654322", "client15",
                new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));

    }

    private void seedUserBusiness() {
        business.save(new UserBusiness(1L, 2L, "Cong ty Le Lê Hoàng Trình", null));
        business.save(new UserBusiness(2L, 3L, "Cong ty Nguyễn Xuân Phúc", DateUtil.setDateLong(-35)));
        business.save(new UserBusiness(3L, 4L, "Cong ty Lý Lê Trọng Phú", DateUtil.setDateLong(-30)));
        business.save(new UserBusiness(4L, 5L, "Cong ty Phạm Văn Hòa", DateUtil.setDateLong(-25)));
        business.save(new UserBusiness(5L, 6L, "Cong ty Nguyễn Xuân Lộc", DateUtil.setDateLong(-20)));
        business.save(new UserBusiness(6L, 7L, "Cong ty Nguyễn Thành Dương", DateUtil.setDateLong(-15)));
        business.save(new UserBusiness(7L, 8L, "Cong ty HoangLong", DateUtil.setDateLong(-10)));
        business.save(new UserBusiness(8L, 9L, "Cong ty Hung Nguyen", DateUtil.setDateLong(-5)));
        business.save(new UserBusiness(9L, 10L, "Cong ty Thanh Phat", DateUtil.setDateLong(0)));
        business.save(new UserBusiness(10L, 11L, "Cong ty Ha Noi", DateUtil.setDateLong(0)));
    }

    private void seedUserFreelancer() {
        freelancer.save(new UserFreelancer(1L, 2L, "Hà Nội", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm", "Chứng chỉ", DateUtil.getTimeLongCurrent()));
        freelancer.save(new UserFreelancer(2L, 3L, "HCM", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm", "Chứng chỉ", DateUtil.getTimeLongCurrent()));
        freelancer.save(new UserFreelancer(3L, 4L, "Đà Nẵng", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm", "Chứng chỉ", DateUtil.getTimeLongCurrent()));
        freelancer.save(new UserFreelancer(4L, 5L, "Quảng Bình", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm", "Chứng chỉ", DateUtil.getTimeLongCurrent()));
        freelancer.save(new UserFreelancer(5L, 6L, "Quảng Ninh", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm", "Chứng chỉ", DateUtil.getTimeLongCurrent()));
        freelancer.save(new UserFreelancer(6L, 7L, "Hà Nam", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm", "Chứng chỉ", DateUtil.getTimeLongCurrent()));
        freelancer.save(new UserFreelancer(7L, 8L, "Hòa Bình", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm", "Chứng chỉ", DateUtil.getTimeLongCurrent()));
        freelancer.save(new UserFreelancer(8L, 9L, "Hà Giang", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm", "Chứng chỉ", DateUtil.getTimeLongCurrent()));
        freelancer.save(new UserFreelancer(9L, 10L, "Hà Tĩnh", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm", "Chứng chỉ", DateUtil.getTimeLongCurrent()));
        freelancer.save(new UserFreelancer(10L, 11L, "Vinh", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm", "Chứng chỉ", DateUtil.getTimeLongCurrent()));
    }

    private void seedSkill() {
        skillRepository.save(new Skill(1L, "JavaScript",1));
        skillRepository.save(new Skill(2L, "Python",1));
        skillRepository.save(new Skill(3L, "C/C++",1));
        skillRepository.save(new Skill(4L, "Java",1));
        skillRepository.save(new Skill(5L, "PHP",1));
        skillRepository.save(new Skill(6L, "Swift",1));
        skillRepository.save(new Skill(7L, "C# (C-Sharp)",1));
        skillRepository.save(new Skill(8L, "Ruby",1));
        skillRepository.save(new Skill(9L, "Objective-C",1));
        skillRepository.save(new Skill(10L, "SQL",1));

    }

    private void seedJob() {
        jobRepository.save((new Job(1L, 1L, 1L, 1L,
                "Wix/Wordpress Website landing page",
                "Job description: - Stage of work: Basic design and construction design - Deploy design documents in each phase and according to the Company's regulations and standards. - Additional structure details and.",
                100.0, 0, DateUtil.setDateLong(-40), DateUtil.setDateLong(-45), 1)));
        jobRepository.save((new Job(2L, 1L, 2L, 2L,
                "Build a web app to exchange old books",
                "Description: A website for students to exchange books with each other, the upper class gives way to the lower class, the scope is in a university Requirements: - Build a website from back - end to front",
                200.0, 0, DateUtil.setDateLong(-40), DateUtil.setDateLong(-40), 1)));
        jobRepository.save((new Job(3L, 2L, 3L, 3L,
                "Sales Wordpress",
                "create a web-based payment sales website (one pay), create automatic sms, create a simple, easy-to-see interface, can use a ready-made theme. Purchase via voucher",
                800.0, 0, DateUtil.setDateLong(-38), DateUtil.setDateLong(-38), 1)));
        jobRepository.save((new Job(4L, 3L, 1L, 1L,
                "Optimizing WordPress website interface",
                "I urgently need to recruit 1 friend who is proficient in wordpress interface code to optimize the newly created company website",
                700.0, 0, DateUtil.setDateLong(-35), DateUtil.setDateLong(-35), 1)));
        jobRepository.save((new Job(5L, 3L, 2L, 3L,
                "WIX developer",
                "Hello're currently know about fundamental WIX experienced at least two years project work lau dai Thanks",
                600.0, 0, DateUtil.setDateLong(-30), DateUtil.setDateLong(-30), 1)));
        jobRepository.save((new Job(6L, 4L, 2L, 3L,
                "Start of React ",
                "We have some devs available on our side and want to find a partner to work on ReactJS. If you want to share, please contact us. Thank you for reading",
                300.0, 1, DateUtil.setDateLong(-25), DateUtil.setDateLong(-25), 1)));
        jobRepository.save((new Job(7L, 4L, 3L, 3L,
                "Looking for ReactJS, Vuejs, ReactNative devs for commercial app projects",
                "There is a project on the application system for commerce Sales, and chat to the shop If you have experience with ReactJS, VueJS, ReatNative, please message me to discuss and cooperate. Exchange",
                100.0, 1, DateUtil.setDateLong(-22), DateUtil.setDateLong(-22), 1)));
        jobRepository.save((new Job(8L, 5L, 3L, 3L,
                "Create ads for Jwplayer ",
                "Hi. I need to find a friend who knows about Jwplayer to program advertising and security features for the player. Advertising Features: I need 1 skippable video ad before playing main video, and 1",
                200.0, 1, DateUtil.setDateLong(-21), DateUtil.setDateLong(-21), 1)));
        jobRepository.save((new Job(9L, 5L, 2L, 3L,
                "Find someone to clone a PHP or Lavarel source website",
                "As the title says, I need to find someone to clone the website required: Hako.re Only use a few functions on the target site, not exactly the same",
                400.0, 1, DateUtil.setDateLong(-21), DateUtil.setDateLong(-21), 1)));
        jobRepository.save((new Job(10L, 6L, 1L, 1L,
                "Setup report for Woocommerce, statistics of advertising effectiveness from Google, FB, revenue",
                "I want to find a friend/team, Setup data from eshop - running on woocommerce. To be able to track events from Google Ads, Facebook Ads, revenue.. to generate total reports on Google",
                600.0, 1, DateUtil.setDateLong(-15), DateUtil.setDateLong(-15), 1)));
        jobRepository.save((new Job(11L, 6L, 2L, 2L,
                "Key LAMP developer",
                "core position of the DEV team in PHP, Python. The tasks list is in order of importance: - lead foreign teams in following requirements by BA/Testers of VN team. - highlight major issues and",
                800.0, 1, DateUtil.setDateLong(-15), DateUtil.setDateLong(-15), 1)));
        jobRepository.save((new Job(12L, 7L, 3L, 3L,
                "Calculate the main time Noon, Soc time, weather",
                "1. Calculate the main time Noon based on the coordinates that the user chooses to do as the web below: http://www.choichiemtinh.net/dichvu/laplaso/sunrise.php 2. Calculate the intersection time between the weather, date",
                700.0, 1, DateUtil.setDateLong(-12), DateUtil.setDateLong(-12), 1)));
        jobRepository.save((new Job(13L, 7L, 2L, 4L,
                "Programming the form interface to input data into Google Sheets (Spreadsheet)",
                "Need a programmer to make an interface form to enter/edit data on Google Sheets (with Javascript): Order input/view/edit form, customer input form",
                600.0, 1, DateUtil.setDateLong(-10), DateUtil.setDateLong(-10), 1)));
        jobRepository.save((new Job(14L, 8L, 1L, 5L,
                "Write UI for web server about 8 screens",
                "Web server to manage the app's information - Writing UI is the main thing. - Get the database to display on the web ",
                600.0, 1, DateUtil.setDateLong(-10), DateUtil.setDateLong(-10), 1)));
        jobRepository.save((new Job(15L, 9L, 2L, 6L,
                "Need to recruit React Js Developer",
                " Need to recruit 2 react js programmers - Job: Get tasks and develop features as required",
                500.0, 1, DateUtil.setDateLong(-8), DateUtil.setDateLong(-8), 1)));
        jobRepository.save((new Job(16L, 9L, 3L, 1L,
                "Looking for a friend to program an extension for chrome",
                "I need to find an experienced chrome extension programmer If you can do it, please contact me to receive project information Thank you very much",
                400.0, 1, DateUtil.setDateLong(-7), DateUtil.setDateLong(-7), 1)));
        jobRepository.save((new Job(17L, 10L, 1L, 4L,
                "Hire a coder or teacher to get the signkey to ajax the login page",
                "I need to find a teacher or hire a coder to get the sign key of the Lazada floor, to ajax my seller's order list page, to save browsing data. (If reputable, I will introduce many customers",
                400.0, 1, DateUtil.setDateLong(0), DateUtil.setDateLong(0), 1)));
        jobRepository.save((new Job(18L, 10L, 2L, 3L,
                "Edit template for php source",
                "Hello, I am looking for a main partner to edit the template for the source code of php, wordpress. Reasonable price and working cooperation will be long term cooperation for upcoming projects",
                300.0, 1, DateUtil.setDateLong(0), DateUtil.setDateLong(0), 1)));
        jobRepository.save((new Job(19L, 10L, 3L, 3L,
                "Laravel Developer",
                "Hi, I have a lot of problems related to using Laravel because I want to know more details about this application. ",
                200.0, 1, DateUtil.setDateLong(0), DateUtil.setDateLong(0), 1)));

    }

    private void seedOtherSkill() {
        otherSkillRepository.save(new OtherSkill(1L, 1L, 1L));
        otherSkillRepository.save(new OtherSkill(2L, 1L, 2L));
        otherSkillRepository.save(new OtherSkill(3L, 1L, 3L));
        otherSkillRepository.save(new OtherSkill(4L, 1L, 4L));
        otherSkillRepository.save(new OtherSkill(5L, 2L, 5L));
        otherSkillRepository.save(new OtherSkill(6L, 2L, 6L));
        otherSkillRepository.save(new OtherSkill(7L, 2L, 7L));
        otherSkillRepository.save(new OtherSkill(8L, 3L, 1L));
        otherSkillRepository.save(new OtherSkill(9L, 3L, 2L));
        otherSkillRepository.save(new OtherSkill(10L, 4L, 1L));
        otherSkillRepository.save(new OtherSkill(11L, 4L, 2L));
        otherSkillRepository.save(new OtherSkill(12L, 5L, 3L));
        otherSkillRepository.save(new OtherSkill(13L, 5L, 4L));
        otherSkillRepository.save(new OtherSkill(14L, 6L, 5L));
        otherSkillRepository.save(new OtherSkill(15L, 6L, 6L));
        otherSkillRepository.save(new OtherSkill(16L, 7L, 3L));
        otherSkillRepository.save(new OtherSkill(17L, 7L, 4L));
        otherSkillRepository.save(new OtherSkill(18L, 8L, 4L));
        otherSkillRepository.save(new OtherSkill(19L, 9L, 4L));
        otherSkillRepository.save(new OtherSkill(20L, 10L, 4L));
        otherSkillRepository.save(new OtherSkill(21L, 11L, 4L));
        otherSkillRepository.save(new OtherSkill(22L, 12L, 4L));
        otherSkillRepository.save(new OtherSkill(23L, 13L, 4L));
        otherSkillRepository.save(new OtherSkill(24L, 14L, 4L));
        otherSkillRepository.save(new OtherSkill(25L, 15L, 4L));
        otherSkillRepository.save(new OtherSkill(26L, 16L, 4L));
        otherSkillRepository.save(new OtherSkill(27L, 17L, 4L));
        otherSkillRepository.save(new OtherSkill(28L, 18L, 4L));
        otherSkillRepository.save(new OtherSkill(29L, 19L, 4L));


    }

    private void seedHasSkill() {
        hasSkillRepository.save(new HasSkill(1L, 1L, 1L));
        hasSkillRepository.save(new HasSkill(2L, 1L, 2L));
        hasSkillRepository.save(new HasSkill(3L, 1L, 3L));
        hasSkillRepository.save(new HasSkill(4L, 1L, 4L));
        hasSkillRepository.save(new HasSkill(5L, 2L, 5L));
        hasSkillRepository.save(new HasSkill(6L, 2L, 6L));
        hasSkillRepository.save(new HasSkill(7L, 2L, 7L));
        hasSkillRepository.save(new HasSkill(8L, 3L, 1L));
        hasSkillRepository.save(new HasSkill(9L, 3L, 2L));
        hasSkillRepository.save(new HasSkill(10L, 4L, 1L));
        hasSkillRepository.save(new HasSkill(11L, 4L, 2L));
        hasSkillRepository.save(new HasSkill(12L, 5L, 3L));
        hasSkillRepository.save(new HasSkill(13L, 5L, 4L));
        hasSkillRepository.save(new HasSkill(14L, 6L, 5L));
        hasSkillRepository.save(new HasSkill(15L, 6L, 6L));
        hasSkillRepository.save(new HasSkill(16L, 7L, 3L));
        hasSkillRepository.save(new HasSkill(17L, 7L, 4L));
        hasSkillRepository.save(new HasSkill(18L, 8L, 4L));
        hasSkillRepository.save(new HasSkill(19L, 8L, 5L));
        hasSkillRepository.save(new HasSkill(20L, 8L, 6L));
        hasSkillRepository.save(new HasSkill(21L, 9L, 1L));
        hasSkillRepository.save(new HasSkill(22L, 9L, 2L));
        hasSkillRepository.save(new HasSkill(23L, 9L, 3L));
        hasSkillRepository.save(new HasSkill(24L, 9L, 4L));
        hasSkillRepository.save(new HasSkill(25L, 10L, 6L));
        hasSkillRepository.save(new HasSkill(26L, 10L, 7L));
        hasSkillRepository.save(new HasSkill(27L, 10L, 8L));
        hasSkillRepository.save(new HasSkill(28L, 10L, 9L));
        hasSkillRepository.save(new HasSkill(29L, 10L, 10L));
    }

    private void seedProposalStatus(){
        proposalStatus.save(new ProposalStatusCatalog(1L,"Đã gửi", DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(),1));
        proposalStatus.save(new ProposalStatusCatalog(2L,"Chấp thuận", DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(),1));
        proposalStatus.save(new ProposalStatusCatalog(3L,"Hoàn thành", DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(),1));
        proposalStatus.save(new ProposalStatusCatalog(4L,"Hủy", DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(),1));

    }
    private void seedProposal(){
        proposal.save(new Proposal(1L ,DateUtil.getTimeLongCurrent(), 200.0, "I look forward to working with you", 2L, 1L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(2L ,DateUtil.getTimeLongCurrent(), 200.1, "I look forward to working with you", 3L, 1L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(3L ,DateUtil.getTimeLongCurrent(), 200.2, "I look forward to working with you", 4L, 1L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(4L ,DateUtil.getTimeLongCurrent(), 200.3, "I look forward to working with you", 5L, 2L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(5L ,DateUtil.getTimeLongCurrent(), 200.4, "I look forward to working with you", 6L, 2L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(6L ,DateUtil.getTimeLongCurrent(), 200.5, "I look forward to working with you", 7L, 2L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(7L ,DateUtil.getTimeLongCurrent(), 200.6, "I look forward to working with you", 1L, 3L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(8L ,DateUtil.getTimeLongCurrent(), 200.7, "I look forward to working with you", 3L, 3L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(9L ,DateUtil.getTimeLongCurrent(), 200.8, "I look forward to working with you", 2L, 4L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(10L ,DateUtil.getTimeLongCurrent(), 200.9, "I look forward to working with you", 4L, 4L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(11L ,DateUtil.getTimeLongCurrent(), 200.10, "I look forward to working with you", 5L, 5L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(12L ,DateUtil.getTimeLongCurrent(), 200.11, "I look forward to working with you", 6L, 5L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(13L ,DateUtil.getTimeLongCurrent(), 200.12, "I look forward to working with you", 7L, 6L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(14L ,DateUtil.getTimeLongCurrent(), 200.13, "I look forward to working with you", 8L, 6L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(15L ,DateUtil.getTimeLongCurrent(), 200.14, "I look forward to working with you", 9L, 7L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(16L ,DateUtil.getTimeLongCurrent(), 200.15, "I look forward to working with you", 10L, 8L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(17L ,DateUtil.getTimeLongCurrent(), 200.16, "I look forward to working with you", 9L, 8L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(18L ,DateUtil.getTimeLongCurrent(), 200.17, "I look forward to working with you", 1L, 9L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(19L ,DateUtil.getTimeLongCurrent(), 200.18, "I look forward to working with you", 2L, 9L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(20L ,DateUtil.getTimeLongCurrent(), 200.19, "I look forward to working with you", 2L, 10L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(21L ,DateUtil.getTimeLongCurrent(), 200.20, "I look forward to working with you", 3L, 10L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(22L ,DateUtil.getTimeLongCurrent(), 200.21, "I look forward to working with you", 4L, 11L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(23L ,DateUtil.getTimeLongCurrent(), 200.22, "I look forward to working with you", 5L, 11L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(24L ,DateUtil.getTimeLongCurrent(), 200.23, "I look forward to working with you", 1L, 12L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(25L ,DateUtil.getTimeLongCurrent(), 200.24, "I look forward to working with you", 2L, 13L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(26L ,DateUtil.getTimeLongCurrent(), 200.25, "I look forward to working with you", 3L, 13L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(27L ,DateUtil.getTimeLongCurrent(), 200.26, "I look forward to working with you", 4L, 14L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(28L ,DateUtil.getTimeLongCurrent(), 200.27, "I look forward to working with you", 5L, 14L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(29L ,DateUtil.getTimeLongCurrent(), 200.28, "I look forward to working with you", 6L, 15L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(30L ,DateUtil.getTimeLongCurrent(), 200.29, "I look forward to working with you", 7L, 15L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(31L ,DateUtil.getTimeLongCurrent(), 200.30, "I look forward to working with you", 8L, 16L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(32L ,DateUtil.getTimeLongCurrent(), 200.31, "I look forward to working with you", 10L, 16L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(33L ,DateUtil.getTimeLongCurrent(), 200.32, "I look forward to working with you", 9L, 17L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(34L ,DateUtil.getTimeLongCurrent(), 200.33, "I look forward to working with you", 8L, 17L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(35L ,DateUtil.getTimeLongCurrent(), 200.34, "I look forward to working with you", 7L, 18L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(36L ,DateUtil.getTimeLongCurrent(), 200.35, "I look forward to working with you", 6L, 19L, 1L)); //Đề xuất được gửi
        proposal.save(new Proposal(37L ,DateUtil.getTimeLongCurrent(), 200.36, "I look forward to working with you", 5L, 19L, 1L)); //Đề xuất được gửi

    }
}
