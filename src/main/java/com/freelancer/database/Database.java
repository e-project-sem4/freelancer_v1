package com.freelancer.database;

import java.util.ArrayList;
import java.util.Arrays;

import com.freelancer.model.*;
import com.freelancer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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
	@Autowired
	ChatKeyUserRepository chatKeyUserRepository;
	@Autowired
	TransactionRepository transactionRepository;

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
//			seedChatUserKey();
			seedTransaction();
		}
	}

	// public Long DateUtil.getTimeLongCurrent() {
	// Date d = new DateUtil.getTimeLongCurrent();
	// return d.getTime();
	// }
	private void seedComplexity() {
		complexityRepository.save(new Complexity(1L, "Easy", 1));
		complexityRepository.save(new Complexity(2L, "Intermediate", 1));
		complexityRepository.save(new Complexity(3L, "Hard", 1));

	}

	private void seedExpected() {
		edRepository.save(new ExpectedDuration(1L, "1 day", 1));
		edRepository.save(new ExpectedDuration(2L, "2-5 days", 1));
		edRepository.save(new ExpectedDuration(3L, "5-10 days", 1));
		edRepository.save(new ExpectedDuration(4L, "less than 1 month", 1));
		edRepository.save(new ExpectedDuration(5L, "1-3 months", 1));
		edRepository.save(new ExpectedDuration(6L, "3-6 months", 1));
		edRepository.save(new ExpectedDuration(7L, "6 or more months", 1));
	}

	private void seedUserAccount() {

		userService.signup(new User(1L, "admin", "admin@gmail.com", "admin", "0987654321", "admin",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)), 2344.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(2L, "client", "client@gmail.com", "client", "0987654322", "client",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 2344.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(3L, "client3", "client3@gmail.com", "client", "0987654323", "client3",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 34.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(4L, "client4", "client4@gmail.com", "client", "0987654324", "client4",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 13.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(5L, "client5", "client5@gmail.com", "client", "0987654325", "client5",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 200.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(6L, "client6", "client6@gmail.com", "client", "0987654326", "client6",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 12.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(7L, "client7", "client7@gmail.com", "client", "0987654327", "client7",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 44.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(8L, "client8", "client8@gmail.com", "client", "0987654328", "client8",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 44.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(9L, "client9", "client9@gmail.com", "client", "0987654322", "client9",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(10L, "client10", "client10@gmail.com", "client", "0987654322", "client10",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(11L, "client11", "client11@gmail.com", "client", "0987654322", "client11",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(12L, "client12", "client12@gmail.com", "client", "0987654322", "client12",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(13L, "client13", "client13@gmail.com", "client", "0987654322", "client13",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(14L, "client14", "client14@gmail.com", "client", "0987654322", "client14",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		userService.signup(new User(15L, "client15", "client15@gmail.com", "client", "0987654322", "client15",
				new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)), 50.0, DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));

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
		business.save(new UserBusiness(11L, 12L, "Cong ty Ha Nam", DateUtil.setDateLong(0)));
		business.save(new UserBusiness(12L, 13L, "Cong ty Quảng Bình", DateUtil.setDateLong(0)));
		business.save(new UserBusiness(13L, 14L, "Cong ty Quảng Ninh", DateUtil.setDateLong(0)));
		business.save(new UserBusiness(14L, 15L, "Cong ty Mai Hồng Vũ", DateUtil.setDateLong(0)));
	}

	private void seedSkill() {
		skillRepository.save(new Skill(1L, "JavaScript", 1));
		skillRepository.save(new Skill(2L, "Python", 1));
		skillRepository.save(new Skill(3L, "C/C++", 1));
		skillRepository.save(new Skill(4L, "Java", 1));
		skillRepository.save(new Skill(5L, "PHP", 1));
		skillRepository.save(new Skill(6L, "Swift", 1));
		skillRepository.save(new Skill(7L, "C# (C-Sharp)", 1));
		skillRepository.save(new Skill(8L, "Ruby", 1));
		skillRepository.save(new Skill(9L, "Objective-C", 1));
		skillRepository.save(new Skill(10L, "SQL", 1));

	}

	private void seedUserFreelancer() {
		freelancer.save(new UserFreelancer(1L, 2L, "Hà Nội", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm",
				"Chứng chỉ", DateUtil.getTimeLongCurrent(), 1));
		freelancer.save(new UserFreelancer(2L, 3L, "HCM", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm", "Chứng chỉ",
				DateUtil.getTimeLongCurrent(), 1));
		freelancer.save(new UserFreelancer(3L, 4L, "Đà Nẵng", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm",
				"Chứng chỉ", DateUtil.getTimeLongCurrent(), 1));
		freelancer.save(new UserFreelancer(4L, 5L, "Quảng Bình", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm",
				"Chứng chỉ", DateUtil.getTimeLongCurrent(), 1));
		freelancer.save(new UserFreelancer(5L, 6L, "Quảng Ninh", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm",
				"Chứng chỉ", DateUtil.getTimeLongCurrent(), 1));
		freelancer.save(new UserFreelancer(6L, 7L, "Hà Nam", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm",
				"Chứng chỉ", DateUtil.getTimeLongCurrent(), 1));
		freelancer.save(new UserFreelancer(7L, 8L, "Hòa Bình", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm",
				"Chứng chỉ", DateUtil.getTimeLongCurrent(), 1));
		freelancer.save(new UserFreelancer(8L, 9L, "Hà Giang", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm",
				"Chứng chỉ", DateUtil.getTimeLongCurrent(), 0));
		freelancer.save(new UserFreelancer(9L, 10L, "Hà Tĩnh", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm",
				"Chứng chỉ", DateUtil.getTimeLongCurrent(), 0));
		freelancer.save(new UserFreelancer(10L, 11L, "Vinh", "Tôi Là 1 Freelancer có nhiều năm kinh nghiệm",
				"Chứng chỉ", DateUtil.getTimeLongCurrent(), 0));
	}

	private void seedJob() {
		jobRepository.save((new Job(1L, 1L, 1L, 1L, "Wix/Wordpress Website landing page",
				"Job description: - Stage of work: Basic design and construction design - Deploy design documents in each phase and according to the Company's regulations and standards. - Additional structure details and.",
				100.0, 0, DateUtil.setDateLong(-40), DateUtil.setDateLong(-45), -1)));
		jobRepository.save((new Job(2L, 1L, 2L, 2L, "Build a web app to exchange old books",
				"Description: A website for students to exchange books with each other, the upper class gives way to the lower class, the scope is in a university Requirements: - Build a website from back - end to front",
				200.0, 0, DateUtil.setDateLong(-40), DateUtil.setDateLong(-40), -1)));
		jobRepository.save((new Job(3L, 2L, 3L, 3L, "Sales Wordpress",
				"create a web-based payment sales website (one pay), create automatic sms, create a simple, easy-to-see interface, can use a ready-made theme. Purchase via voucher",
				800.0, 1, DateUtil.setDateLong(-38), DateUtil.setDateLong(-38), 2)));
		jobRepository.save((new Job(4L, 3L, 1L, 1L, "Optimizing WordPress website interface",
				"I urgently need to recruit 1 friend who is proficient in wordpress interface code to optimize the newly created company website",
				700.0, 1, DateUtil.setDateLong(-35), DateUtil.setDateLong(-35), 2)));
		jobRepository.save((new Job(5L, 3L, 2L, 3L, "WIX developer",
				"Hello're currently know about fundamental WIX experienced at least two years project work lau dai Thanks",
				600.0, 1, DateUtil.setDateLong(-30), DateUtil.setDateLong(-30), 2)));
		jobRepository.save((new Job(6L, 4L, 2L, 3L, "Start of React ",
				"We have some devs available on our side and want to find a partner to work on ReactJS. If you want to share, please contact us. Thank you for reading",
				300.0, 1, DateUtil.setDateLong(-25), DateUtil.setDateLong(-25), 2)));
		jobRepository.save((new Job(7L, 4L, 3L, 3L,
				"Looking for ReactJS, Vuejs, ReactNative devs for commercial app projects",
				"There is a project on the application system for commerce Sales, and chat to the shop If you have experience with ReactJS, VueJS, ReatNative, please message me to discuss and cooperate. Exchange",
				100.0, 1, DateUtil.setDateLong(-22), DateUtil.setDateLong(-22), 2)));
		jobRepository.save((new Job(8L, 5L, 3L, 3L, "Create ads for Jwplayer ",
				"Hi. I need to find a friend who knows about Jwplayer to program advertising and security features for the player. Advertising Features: I need 1 skippable video ad before playing main video, and 1",
				200.0, 1, DateUtil.setDateLong(-21), DateUtil.setDateLong(-21), 2)));
		jobRepository.save((new Job(9L, 5L, 2L, 3L, "Find someone to clone a PHP or Lavarel source website",
				"As the title says, I need to find someone to clone the website required: Hako.re Only use a few functions on the target site, not exactly the same",
				400.0, 1, DateUtil.setDateLong(-21), DateUtil.setDateLong(-21), 2)));
		jobRepository.save((new Job(10L, 6L, 1L, 1L,
				"Setup report for Woocommerce, statistics of advertising effectiveness from Google, FB, revenue",
				"I want to find a friend/team, Setup data from eshop - running on woocommerce. To be able to track events from Google Ads, Facebook Ads, revenue.. to generate total reports on Google",
				600.0, 1, DateUtil.setDateLong(-15), DateUtil.setDateLong(-15), 2)));
		jobRepository.save((new Job(11L, 6L, 2L, 2L, "Key LAMP developer",
				"core position of the DEV team in PHP, Python. The tasks list is in order of importance: - lead foreign teams in following requirements by BA/Testers of VN team. - highlight major issues and",
				800.0, 1, DateUtil.setDateLong(-15), DateUtil.setDateLong(-15), 2)));
		jobRepository.save((new Job(12L, 7L, 3L, 3L, "Calculate the main time Noon, Soc time, weather",
				"1. Calculate the main time Noon based on the coordinates that the user chooses to do as the web below: http://www.choichiemtinh.net/dichvu/laplaso/sunrise.php 2. Calculate the intersection time between the weather, date",
				700.0, 1, DateUtil.setDateLong(-12), DateUtil.setDateLong(-12), 2)));
		jobRepository.save((new Job(13L, 7L, 2L, 4L,
				"Programming the form interface to input data into Google Sheets (Spreadsheet)",
				"Need a programmer to make an interface form to enter/edit data on Google Sheets (with Javascript): Order input/view/edit form, customer input form",
				600.0, 1, DateUtil.setDateLong(-10), DateUtil.setDateLong(-10), 2)));
		jobRepository.save((new Job(14L, 8L, 1L, 5L, "Write UI for web server about 8 screens",
				"Web server to manage the app's information - Writing UI is the main thing. - Get the database to display on the web ",
				600.0, 1, DateUtil.setDateLong(-10), DateUtil.setDateLong(-10), 2)));
		jobRepository.save((new Job(15L, 9L, 2L, 6L, "Need to recruit React Js Developer",
				" Need to recruit 2 react js programmers - Job: Get tasks and develop features as required", 500.0, 1,
				DateUtil.setDateLong(-8), DateUtil.setDateLong(-8), 3)));
		jobRepository.save((new Job(16L, 9L, 3L, 1L, "Looking for a friend to program an extension for chrome",
				"I need to find an experienced chrome extension programmer If you can do it, please contact me to receive project information Thank you very much",
				400.0, 1, DateUtil.setDateLong(-7), DateUtil.setDateLong(-7), 3)));
		jobRepository.save((new Job(17L, 10L, 1L, 4L,
				"Hire a coder or teacher to get the signkey to ajax the login page",
				"I need to find a teacher or hire a coder to get the sign key of the Lazada floor, to ajax my seller's order list page, to save browsing data. (If reputable, I will introduce many customers",
				400.0, 1, DateUtil.setDateLong(0), DateUtil.setDateLong(0), 3)));
		jobRepository.save((new Job(18L, 10L, 2L, 3L, "Edit template for php source",
				"Hello, I am looking for a main partner to edit the template for the source code of php, wordpress. Reasonable price and working cooperation will be long term cooperation for upcoming projects",
				300.0, 1, DateUtil.setDateLong(0), DateUtil.setDateLong(0), 3)));
		jobRepository.save((new Job(19L, 10L, 3L, 3L, "Laravel Developer",
				"Hi, I have a lot of problems related to using Laravel because I want to know more details about this application. ",
				200.0, 1, DateUtil.setDateLong(0), DateUtil.setDateLong(0), 3)));

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

	private void seedProposalStatus() {
		proposalStatus.save(new ProposalStatusCatalog(1L, "Đã gửi", DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		proposalStatus.save(new ProposalStatusCatalog(2L, "Chấp thuận", DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		proposalStatus.save(new ProposalStatusCatalog(3L, "Hoàn thành", DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		proposalStatus.save(new ProposalStatusCatalog(4L, "Business Hủy", DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		proposalStatus.save(new ProposalStatusCatalog(5L, "FreeLancer Hủy", DateUtil.getTimeLongCurrent(),
				DateUtil.getTimeLongCurrent(), 1));
		proposalStatus.save(
				new ProposalStatusCatalog(99L, "Xóa", DateUtil.getTimeLongCurrent(), DateUtil.getTimeLongCurrent(), 1));

	}

	private void seedProposal() {
		proposal.save(new Proposal(1L ,DateUtil.getTimeLongCurrent(),105.3,"1 look forward to working with you",1L ,3L ,1L ,null,null,null,null));
		proposal.save(new Proposal(2L ,DateUtil.getTimeLongCurrent(),110.3,"I look forward to working",3L ,3L ,2L ,null,null,null,null));
		proposal.save(new Proposal(3L ,DateUtil.getTimeLongCurrent(),100.4,"Please choose me",4L ,3L ,2L ,null,null,null,null));
		proposal.save(new Proposal(4L ,DateUtil.getTimeLongCurrent(),105.4,"I have a lot of experience that suits you",5L ,3L ,4L ,null,null,null,null));
		proposal.save(new Proposal(5L ,DateUtil.getTimeLongCurrent(),110.4,"Let's work together",6L ,3L ,5L ,null,null,null,null));
		proposal.save(new Proposal(6L ,DateUtil.getTimeLongCurrent(),100.1,"1 look forward to working with you",7L ,4L ,1L ,null,null,null,null));
		proposal.save(new Proposal(7L ,DateUtil.getTimeLongCurrent(),105.1,"I look forward to working",8L ,4L ,2L ,null,null,null,null));
		proposal.save(new Proposal(8L ,DateUtil.getTimeLongCurrent(),110.1,"Please choose me",9L ,4L ,2L ,null,null,null,null));
		proposal.save(new Proposal(9L ,DateUtil.getTimeLongCurrent(),100.2,"I have a lot of experience that suits you",10L ,4L ,4L ,null,null,null,null));
		proposal.save(new Proposal(10L ,DateUtil.getTimeLongCurrent(),105.2,"Let's work together",1L ,4L ,5L ,null,null,null,null));
		proposal.save(new Proposal(11L ,DateUtil.getTimeLongCurrent(),110.2,"1 look forward to working with you",2L ,5L ,1L ,null,null,null,null));
		proposal.save(new Proposal(12L ,DateUtil.getTimeLongCurrent(),100.3,"I look forward to working",4L ,5L ,2L ,null,null,null,null));
		proposal.save(new Proposal(13L ,DateUtil.getTimeLongCurrent(),105.3,"Please choose me",5L ,5L ,2L ,null,null,null,null));
		proposal.save(new Proposal(14L ,DateUtil.getTimeLongCurrent(),110.3,"I have a lot of experience that suits you",6L ,5L ,4L ,null,null,null,null));
		proposal.save(new Proposal(15L ,DateUtil.getTimeLongCurrent(),100.4,"Let's work together",7L ,5L ,5L ,null,null,null,null));
		proposal.save(new Proposal(16L ,DateUtil.getTimeLongCurrent(),105.4,"1 look forward to working with you",9L ,6L ,1L ,null,null,null,null));
		proposal.save(new Proposal(17L ,DateUtil.getTimeLongCurrent(),110.4,"I look forward to working",10L ,6L ,2L ,null,null,null,null));
		proposal.save(new Proposal(18L ,DateUtil.getTimeLongCurrent(),100.5,"Please choose me",1L ,6L ,2L ,null,null,null,null));
		proposal.save(new Proposal(19L ,DateUtil.getTimeLongCurrent(),105.5,"I have a lot of experience that suits you",2L ,6L ,4L ,null,null,null,null));
		proposal.save(new Proposal(20L ,DateUtil.getTimeLongCurrent(),110.5,"Let's work together",3L ,6L ,5L ,null,null,null,null));
		proposal.save(new Proposal(21L ,DateUtil.getTimeLongCurrent(),100.2,"1 look forward to working with you",6L ,7L ,1L ,null,null,null,null));
		proposal.save(new Proposal(22L ,DateUtil.getTimeLongCurrent(),105.2,"I look forward to working",7L ,7L ,2L ,null,null,null,null));
		proposal.save(new Proposal(23L ,DateUtil.getTimeLongCurrent(),110.2,"Please choose me",8L ,7L ,2L ,null,null,null,null));
		proposal.save(new Proposal(24L ,DateUtil.getTimeLongCurrent(),100.3,"I have a lot of experience that suits you",9L ,7L ,4L ,null,null,null,null));
		proposal.save(new Proposal(25L ,DateUtil.getTimeLongCurrent(),105.3,"Let's work together",10L ,7L ,5L ,null,null,null,null));
		proposal.save(new Proposal(26L ,DateUtil.getTimeLongCurrent(),110.3,"1 look forward to working with you",1L ,8L ,1L ,null,null,null,null));
		proposal.save(new Proposal(27L ,DateUtil.getTimeLongCurrent(),100.4,"I look forward to working",2L ,8L ,2L ,null,null,null,null));
		proposal.save(new Proposal(28L ,DateUtil.getTimeLongCurrent(),105.4,"Please choose me",3L ,8L ,2L ,null,null,null,null));
		proposal.save(new Proposal(29L ,DateUtil.getTimeLongCurrent(),110.4,"I have a lot of experience that suits you",4L ,8L ,4L ,null,null,null,null));
		proposal.save(new Proposal(30L ,DateUtil.getTimeLongCurrent(),100.5,"Let's work together",6L ,8L ,5L ,null,null,null,null));
		proposal.save(new Proposal(31L ,DateUtil.getTimeLongCurrent(),105.5,"1 look forward to working with you",7L ,9L ,1L ,null,null,null,null));
		proposal.save(new Proposal(32L ,DateUtil.getTimeLongCurrent(),110.5,"I look forward to working",8L ,9L ,2L ,null,null,null,null));
		proposal.save(new Proposal(33L ,DateUtil.getTimeLongCurrent(),100.6,"Please choose me",9L ,9L ,2L ,null,null,null,null));
		proposal.save(new Proposal(34L ,DateUtil.getTimeLongCurrent(),105.6,"I have a lot of experience that suits you",10L ,9L ,4L ,null,null,null,null));
		proposal.save(new Proposal(35L ,DateUtil.getTimeLongCurrent(),110.6,"Let's work together",1L ,9L ,5L ,null,null,null,null));
		proposal.save(new Proposal(36L ,DateUtil.getTimeLongCurrent(),100.3,"1 look forward to working with you",2L ,10L ,1L ,null,null,null,null));
		proposal.save(new Proposal(37L ,DateUtil.getTimeLongCurrent(),105.3,"I look forward to working",3L ,10L ,2L ,null,null,null,null));
		proposal.save(new Proposal(38L ,DateUtil.getTimeLongCurrent(),110.3,"Please choose me",4L ,10L ,2L ,null,null,null,null));
		proposal.save(new Proposal(39L ,DateUtil.getTimeLongCurrent(),100.4,"I have a lot of experience that suits you",5L ,10L ,4L ,null,null,null,null));
		proposal.save(new Proposal(40L ,DateUtil.getTimeLongCurrent(),105.4,"Let's work together",7L ,10L ,5L ,null,null,null,null));
		proposal.save(new Proposal(41L ,DateUtil.getTimeLongCurrent(),110.4,"1 look forward to working with you",8L ,11L ,1L ,null,null,null,null));
		proposal.save(new Proposal(42L ,DateUtil.getTimeLongCurrent(),100.5,"I look forward to working",9L ,11L ,2L ,null,null,null,null));
		proposal.save(new Proposal(43L ,DateUtil.getTimeLongCurrent(),105.5,"Please choose me",10L ,11L ,2L ,null,null,null,null));
		proposal.save(new Proposal(44L ,DateUtil.getTimeLongCurrent(),110.5,"I have a lot of experience that suits you",1L ,11L ,4L ,null,null,null,null));
		proposal.save(new Proposal(45L ,DateUtil.getTimeLongCurrent(),100.6,"Let's work together",2L ,11L ,5L ,null,null,null,null));
		proposal.save(new Proposal(46L ,DateUtil.getTimeLongCurrent(),105.6,"1 look forward to working with you",3L ,12L ,1L ,null,null,null,null));
		proposal.save(new Proposal(47L ,DateUtil.getTimeLongCurrent(),110.6,"I look forward to working",4L ,12L ,2L ,null,null,null,null));
		proposal.save(new Proposal(48L ,DateUtil.getTimeLongCurrent(),100.7,"Please choose me",5L ,12L ,2L ,null,null,null,null));
		proposal.save(new Proposal(49L ,DateUtil.getTimeLongCurrent(),105.7,"I have a lot of experience that suits you",6L ,12L ,4L ,null,null,null,null));
		proposal.save(new Proposal(50L ,DateUtil.getTimeLongCurrent(),110.7,"Let's work together",8L ,12L ,5L ,null,null,null,null));
		proposal.save(new Proposal(51L ,DateUtil.getTimeLongCurrent(),100.4,"1 look forward to working with you",9L ,13L ,1L ,null,null,null,null));
		proposal.save(new Proposal(52L ,DateUtil.getTimeLongCurrent(),105.4,"I look forward to working",10L ,13L ,2L ,null,null,null,null));
		proposal.save(new Proposal(53L ,DateUtil.getTimeLongCurrent(),110.4,"Please choose me",1L ,13L ,2L ,null,null,null,null));
		proposal.save(new Proposal(54L ,DateUtil.getTimeLongCurrent(),100.5,"I have a lot of experience that suits you",2L ,13L ,4L ,null,null,null,null));
		proposal.save(new Proposal(55L ,DateUtil.getTimeLongCurrent(),105.5,"Let's work together",3L ,13L ,5L ,null,null,null,null));
		proposal.save(new Proposal(56L ,DateUtil.getTimeLongCurrent(),110.5,"1 look forward to working with you",4L ,14L ,1L ,null,null,null,null));
		proposal.save(new Proposal(57L ,DateUtil.getTimeLongCurrent(),100.6,"I look forward to working",5L ,14L ,2L ,null,null,null,null));
		proposal.save(new Proposal(58L ,DateUtil.getTimeLongCurrent(),105.6,"Please choose me",6L ,14L ,2L ,null,null,null,null));
		proposal.save(new Proposal(59L ,DateUtil.getTimeLongCurrent(),110.6,"I have a lot of experience that suits you",7L ,14L ,4L ,null,null,null,null));
		proposal.save(new Proposal(60L ,DateUtil.getTimeLongCurrent(),100.7,"Let's work together",9L ,14L ,5L ,null,null,null,null));
		proposal.save(new Proposal(61L ,DateUtil.getTimeLongCurrent(),105.7,"1 look forward to working with you",10L ,15L ,1L ,null,null,null,null));
		proposal.save(new Proposal(62L ,DateUtil.getTimeLongCurrent(),110.7,"I look forward to working",1L ,15L ,2L ,null,null,null,null));
		proposal.save(new Proposal(63L ,DateUtil.getTimeLongCurrent(),100.8,"Please choose me",2L ,15L ,3L ,5,"Good",5,"Good"));
		proposal.save(new Proposal(64L ,DateUtil.getTimeLongCurrent(),105.8,"I have a lot of experience that suits you",3L ,15L ,4L ,null,null,null,null));
		proposal.save(new Proposal(65L ,DateUtil.getTimeLongCurrent(),110.8,"Let's work together",4L ,15L ,5L ,null,null,null,null));
		proposal.save(new Proposal(66L ,DateUtil.getTimeLongCurrent(),100.5,"1 look forward to working with you",5L ,16L ,1L ,null,null,null,null));
		proposal.save(new Proposal(67L ,DateUtil.getTimeLongCurrent(),105.5,"I look forward to working",6L ,16L ,2L ,null,null,null,null));
		proposal.save(new Proposal(68L ,DateUtil.getTimeLongCurrent(),110.5,"Please choose me",7L ,16L ,3L ,5,"Good",5,"Good"));
		proposal.save(new Proposal(69L ,DateUtil.getTimeLongCurrent(),100.6,"I have a lot of experience that suits you",8L ,16L ,4L ,null,null,null,null));
		proposal.save(new Proposal(70L ,DateUtil.getTimeLongCurrent(),105.6,"Let's work together",1L ,16L ,5L ,null,null,null,null));
		proposal.save(new Proposal(71L ,DateUtil.getTimeLongCurrent(),110.6,"1 look forward to working with you",2L ,17L ,1L ,null,null,null,null));
		proposal.save(new Proposal(72L ,DateUtil.getTimeLongCurrent(),100.7,"I look forward to working",3L ,17L ,2L ,null,null,null,null));
		proposal.save(new Proposal(73L ,DateUtil.getTimeLongCurrent(),105.7,"Please choose me",4L ,17L ,3L ,5,"Good",5,"Good"));
		proposal.save(new Proposal(74L ,DateUtil.getTimeLongCurrent(),110.7,"I have a lot of experience that suits you",5L ,17L ,4L ,null,null,null,null));
		proposal.save(new Proposal(75L ,DateUtil.getTimeLongCurrent(),100.8,"Let's work together",6L ,17L ,5L ,null,null,null,null));
		proposal.save(new Proposal(76L ,DateUtil.getTimeLongCurrent(),105.8,"1 look forward to working with you",7L ,18L ,1L ,null,null,null,null));
		proposal.save(new Proposal(77L ,DateUtil.getTimeLongCurrent(),110.8,"I look forward to working",8L ,18L ,2L ,null,null,null,null));
		proposal.save(new Proposal(78L ,DateUtil.getTimeLongCurrent(),100.9,"Please choose me",9L ,18L ,3L ,5,"Good",5,"Good"));
		proposal.save(new Proposal(79L ,DateUtil.getTimeLongCurrent(),105.9,"I have a lot of experience that suits you",1L ,18L ,4L ,null,null,null,null));
		proposal.save(new Proposal(80L ,DateUtil.getTimeLongCurrent(),110.9,"Let's work together",2L ,18L ,5L ,null,null,null,null));
		proposal.save(new Proposal(81L ,DateUtil.getTimeLongCurrent(),100.6,"1 look forward to working with you",3L ,19L ,1L ,null,null,null,null));
		proposal.save(new Proposal(82L ,DateUtil.getTimeLongCurrent(),105.6,"I look forward to working",4L ,19L ,2L ,null,null,null,null));
		proposal.save(new Proposal(83L ,DateUtil.getTimeLongCurrent(),110.6,"Please choose me",5L ,19L ,3L ,5,"Good",5,"Good"));
		proposal.save(new Proposal(84L ,DateUtil.getTimeLongCurrent(),100.7,"I have a lot of experience that suits you",6L ,19L ,4L ,null,null,null,null));
		proposal.save(new Proposal(85L ,DateUtil.getTimeLongCurrent(),105.7,"Let's work together",7L ,19L ,5L ,null,null,null,null));

	}

//	private void seedChatUserKey() {
//		chatKeyUserRepository
//				.save(new ChatKeyUser(1L, 1L, 2L, 2L, "1-2-2", "Build a web app to exchange old books", null));
//		chatKeyUserRepository.save(new ChatKeyUser(2L, 1L, 3L, 3L, "1-3-3", "Sales Wordpress", null));
//		chatKeyUserRepository
//				.save(new ChatKeyUser(3L, 1L, 4L, 4L, "1-4-4", "Optimizing WordPress website interface", null));
//		chatKeyUserRepository
//				.save(new ChatKeyUser(4L, 2L, 1L, 2L, "1-2-2", "Build a web app to exchange old books", null));
//		chatKeyUserRepository.save(new ChatKeyUser(5L, 3L, 1L, 3L, "1-3-3", "Sales Wordpress", null));
//		chatKeyUserRepository
//				.save(new ChatKeyUser(6L, 4L, 1L, 4L, "1-4-4", "Optimizing WordPress website interface", null));
//		chatKeyUserRepository.save(new ChatKeyUser(7L, 3L, 2L, 5L, "3-2-5", "WIX developer", null));
//		chatKeyUserRepository.save(new ChatKeyUser(8L, 2L, 3L, 5L, "3-2-5", "WIX developer", null));
//	}

	private void seedTransaction() {
		// nạp tiền
		transactionRepository
				.save(new Transaction(1L, 300.0, DateUtil.setDateLong(-40), Transaction.TransactionType.RECHARGE, 2L));
		transactionRepository
				.save(new Transaction(2L, 310.0, DateUtil.setDateLong(-39), Transaction.TransactionType.RECHARGE, 3L));
		transactionRepository
				.save(new Transaction(3L, 280.0, DateUtil.setDateLong(-38), Transaction.TransactionType.RECHARGE, 4L));
		transactionRepository
				.save(new Transaction(4L, 100.0, DateUtil.setDateLong(-37), Transaction.TransactionType.RECHARGE, 5L));
		transactionRepository
				.save(new Transaction(5L, 105.0, DateUtil.setDateLong(-36), Transaction.TransactionType.RECHARGE, 6L));
		transactionRepository
				.save(new Transaction(6L, 300.1, DateUtil.setDateLong(-35), Transaction.TransactionType.RECHARGE, 7L));
		transactionRepository
				.save(new Transaction(7L, 310.1, DateUtil.setDateLong(-34), Transaction.TransactionType.RECHARGE, 8L));
		transactionRepository
				.save(new Transaction(8L, 280.1, DateUtil.setDateLong(-33), Transaction.TransactionType.RECHARGE, 9L));
		transactionRepository
				.save(new Transaction(9L, 100.1, DateUtil.setDateLong(-32), Transaction.TransactionType.RECHARGE, 10L));
		transactionRepository.save(
				new Transaction(10L, 105.1, DateUtil.setDateLong(-31), Transaction.TransactionType.RECHARGE, 11L));
		transactionRepository.save(
				new Transaction(11L, 300.2, DateUtil.setDateLong(-30), Transaction.TransactionType.RECHARGE, 12L));
		transactionRepository.save(
				new Transaction(12L, 310.2, DateUtil.setDateLong(-29), Transaction.TransactionType.RECHARGE, 13L));
		transactionRepository.save(
				new Transaction(13L, 280.2, DateUtil.setDateLong(-28), Transaction.TransactionType.RECHARGE, 14L));
		transactionRepository.save(
				new Transaction(14L, 300.1, DateUtil.setDateLong(-27), Transaction.TransactionType.RECHARGE, 15L));
		transactionRepository
				.save(new Transaction(15L, 310.1, DateUtil.setDateLong(-26), Transaction.TransactionType.RECHARGE, 2L));
		transactionRepository
				.save(new Transaction(16L, 280.1, DateUtil.setDateLong(-25), Transaction.TransactionType.RECHARGE, 3L));
		transactionRepository
				.save(new Transaction(17L, 100.1, DateUtil.setDateLong(-24), Transaction.TransactionType.RECHARGE, 4L));
		transactionRepository
				.save(new Transaction(18L, 105.1, DateUtil.setDateLong(-23), Transaction.TransactionType.RECHARGE, 5L));
		transactionRepository
				.save(new Transaction(19L, 300.2, DateUtil.setDateLong(-22), Transaction.TransactionType.RECHARGE, 6L));
		transactionRepository
				.save(new Transaction(20L, 310.2, DateUtil.setDateLong(-21), Transaction.TransactionType.RECHARGE, 7L));
		transactionRepository
				.save(new Transaction(21L, 280.2, DateUtil.setDateLong(-20), Transaction.TransactionType.RECHARGE, 8L));
		transactionRepository
				.save(new Transaction(22L, 100.2, DateUtil.setDateLong(-19), Transaction.TransactionType.RECHARGE, 9L));
		transactionRepository.save(
				new Transaction(23L, 105.2, DateUtil.setDateLong(-18), Transaction.TransactionType.RECHARGE, 10L));
		transactionRepository.save(
				new Transaction(24L, 300.3, DateUtil.setDateLong(-17), Transaction.TransactionType.RECHARGE, 11L));
		transactionRepository.save(
				new Transaction(25L, 310.3, DateUtil.setDateLong(-16), Transaction.TransactionType.RECHARGE, 12L));
		transactionRepository.save(
				new Transaction(26L, 280.3, DateUtil.setDateLong(-15), Transaction.TransactionType.RECHARGE, 13L));
		transactionRepository.save(
				new Transaction(27L, 300.2, DateUtil.setDateLong(-14), Transaction.TransactionType.RECHARGE, 14L));
		transactionRepository.save(
				new Transaction(28L, 310.2, DateUtil.setDateLong(-13), Transaction.TransactionType.RECHARGE, 15L));
		transactionRepository
				.save(new Transaction(29L, 280.2, DateUtil.setDateLong(-12), Transaction.TransactionType.RECHARGE, 2L));
		transactionRepository
				.save(new Transaction(30L, 100.2, DateUtil.setDateLong(-11), Transaction.TransactionType.RECHARGE, 3L));
		transactionRepository
				.save(new Transaction(31L, 105.2, DateUtil.setDateLong(-10), Transaction.TransactionType.RECHARGE, 4L));
		transactionRepository
				.save(new Transaction(32L, 300.3, DateUtil.setDateLong(-9), Transaction.TransactionType.RECHARGE, 5L));
		transactionRepository
				.save(new Transaction(33L, 310.3, DateUtil.setDateLong(-8), Transaction.TransactionType.RECHARGE, 6L));
		transactionRepository
				.save(new Transaction(34L, 280.3, DateUtil.setDateLong(-7), Transaction.TransactionType.RECHARGE, 7L));
		transactionRepository
				.save(new Transaction(35L, 100.3, DateUtil.setDateLong(-6), Transaction.TransactionType.RECHARGE, 8L));
		transactionRepository
				.save(new Transaction(36L, 105.3, DateUtil.setDateLong(-5), Transaction.TransactionType.RECHARGE, 9L));
		transactionRepository
				.save(new Transaction(37L, 300.4, DateUtil.setDateLong(-4), Transaction.TransactionType.RECHARGE, 10L));
		transactionRepository
				.save(new Transaction(38L, 310.4, DateUtil.setDateLong(-3), Transaction.TransactionType.RECHARGE, 11L));
		transactionRepository
				.save(new Transaction(39L, 280.4, DateUtil.setDateLong(-2), Transaction.TransactionType.RECHARGE, 12L));
		transactionRepository
				.save(new Transaction(40L, 300.3, DateUtil.setDateLong(-1), Transaction.TransactionType.RECHARGE, 13L));
		transactionRepository
				.save(new Transaction(41L, 310.3, DateUtil.setDateLong(0), Transaction.TransactionType.RECHARGE, 14L));
		transactionRepository.save(
				new Transaction(42L, 280.3, DateUtil.setDateLong(-40), Transaction.TransactionType.RECHARGE, 15L));
		transactionRepository
				.save(new Transaction(43L, 100.3, DateUtil.setDateLong(-39), Transaction.TransactionType.RECHARGE, 2L));
		transactionRepository
				.save(new Transaction(44L, 105.3, DateUtil.setDateLong(-38), Transaction.TransactionType.RECHARGE, 3L));
		transactionRepository
				.save(new Transaction(45L, 300.4, DateUtil.setDateLong(-37), Transaction.TransactionType.RECHARGE, 4L));
		transactionRepository
				.save(new Transaction(46L, 310.4, DateUtil.setDateLong(-36), Transaction.TransactionType.RECHARGE, 5L));
		transactionRepository
				.save(new Transaction(47L, 280.4, DateUtil.setDateLong(-35), Transaction.TransactionType.RECHARGE, 6L));
		transactionRepository
				.save(new Transaction(48L, 100.4, DateUtil.setDateLong(-34), Transaction.TransactionType.RECHARGE, 7L));
		transactionRepository
				.save(new Transaction(49L, 105.4, DateUtil.setDateLong(-33), Transaction.TransactionType.RECHARGE, 8L));
		transactionRepository
				.save(new Transaction(50L, 300.5, DateUtil.setDateLong(-32), Transaction.TransactionType.RECHARGE, 9L));
		transactionRepository.save(
				new Transaction(51L, 310.5, DateUtil.setDateLong(-31), Transaction.TransactionType.RECHARGE, 10L));
		transactionRepository.save(
				new Transaction(52L, 280.5, DateUtil.setDateLong(-30), Transaction.TransactionType.RECHARGE, 11L));
		transactionRepository.save(
				new Transaction(53L, 300.4, DateUtil.setDateLong(-29), Transaction.TransactionType.RECHARGE, 12L));
		transactionRepository.save(
				new Transaction(54L, 310.4, DateUtil.setDateLong(-28), Transaction.TransactionType.RECHARGE, 13L));
		transactionRepository.save(
				new Transaction(55L, 280.4, DateUtil.setDateLong(-27), Transaction.TransactionType.RECHARGE, 14L));
		transactionRepository.save(
				new Transaction(56L, 100.4, DateUtil.setDateLong(-26), Transaction.TransactionType.RECHARGE, 15L));
		transactionRepository
				.save(new Transaction(57L, 105.4, DateUtil.setDateLong(-25), Transaction.TransactionType.RECHARGE, 2L));
		transactionRepository
				.save(new Transaction(58L, 300.5, DateUtil.setDateLong(-24), Transaction.TransactionType.RECHARGE, 3L));
		transactionRepository
				.save(new Transaction(59L, 310.5, DateUtil.setDateLong(-23), Transaction.TransactionType.RECHARGE, 4L));
		transactionRepository
				.save(new Transaction(60L, 280.5, DateUtil.setDateLong(-22), Transaction.TransactionType.RECHARGE, 5L));
		transactionRepository
				.save(new Transaction(61L, 100.5, DateUtil.setDateLong(-21), Transaction.TransactionType.RECHARGE, 6L));
		transactionRepository
				.save(new Transaction(62L, 105.5, DateUtil.setDateLong(-20), Transaction.TransactionType.RECHARGE, 7L));
		transactionRepository
				.save(new Transaction(63L, 300.6, DateUtil.setDateLong(-19), Transaction.TransactionType.RECHARGE, 8L));
		transactionRepository
				.save(new Transaction(64L, 310.6, DateUtil.setDateLong(-18), Transaction.TransactionType.RECHARGE, 9L));
		transactionRepository.save(
				new Transaction(65L, 280.6, DateUtil.setDateLong(-17), Transaction.TransactionType.RECHARGE, 10L));
		transactionRepository.save(
				new Transaction(66L, 300.5, DateUtil.setDateLong(-16), Transaction.TransactionType.RECHARGE, 11L));
		transactionRepository.save(
				new Transaction(67L, 310.5, DateUtil.setDateLong(-15), Transaction.TransactionType.RECHARGE, 12L));
		transactionRepository.save(
				new Transaction(68L, 280.5, DateUtil.setDateLong(-14), Transaction.TransactionType.RECHARGE, 13L));
		transactionRepository.save(
				new Transaction(69L, 100.5, DateUtil.setDateLong(-13), Transaction.TransactionType.RECHARGE, 14L));
		transactionRepository.save(
				new Transaction(70L, 105.5, DateUtil.setDateLong(-12), Transaction.TransactionType.RECHARGE, 15L));
		transactionRepository
				.save(new Transaction(71L, 300.6, DateUtil.setDateLong(-11), Transaction.TransactionType.RECHARGE, 2L));
		transactionRepository
				.save(new Transaction(72L, 310.6, DateUtil.setDateLong(-10), Transaction.TransactionType.RECHARGE, 3L));
		transactionRepository
				.save(new Transaction(73L, 280.6, DateUtil.setDateLong(-9), Transaction.TransactionType.RECHARGE, 4L));
		transactionRepository
				.save(new Transaction(74L, 100.6, DateUtil.setDateLong(-8), Transaction.TransactionType.RECHARGE, 5L));
		transactionRepository
				.save(new Transaction(75L, 105.6, DateUtil.setDateLong(-7), Transaction.TransactionType.RECHARGE, 6L));
		transactionRepository
				.save(new Transaction(76L, 300.7, DateUtil.setDateLong(-6), Transaction.TransactionType.RECHARGE, 7L));
		transactionRepository
				.save(new Transaction(77L, 310.7, DateUtil.setDateLong(-5), Transaction.TransactionType.RECHARGE, 8L));
		transactionRepository
				.save(new Transaction(78L, 280.7, DateUtil.setDateLong(-4), Transaction.TransactionType.RECHARGE, 9L));
		transactionRepository
				.save(new Transaction(79L, 300.6, DateUtil.setDateLong(-3), Transaction.TransactionType.RECHARGE, 10L));
		transactionRepository
				.save(new Transaction(80L, 310.6, DateUtil.setDateLong(-2), Transaction.TransactionType.RECHARGE, 11L));
		transactionRepository
				.save(new Transaction(81L, 280.6, DateUtil.setDateLong(-1), Transaction.TransactionType.RECHARGE, 12L));
		transactionRepository
				.save(new Transaction(82L, 100.6, DateUtil.setDateLong(0), Transaction.TransactionType.RECHARGE, 13L));
		transactionRepository.save(
				new Transaction(83L, 105.6, DateUtil.setDateLong(-40), Transaction.TransactionType.RECHARGE, 14L));
		transactionRepository.save(
				new Transaction(84L, 300.7, DateUtil.setDateLong(-39), Transaction.TransactionType.RECHARGE, 15L));
		transactionRepository
				.save(new Transaction(85L, 310.7, DateUtil.setDateLong(-38), Transaction.TransactionType.RECHARGE, 2L));
		transactionRepository
				.save(new Transaction(86L, 280.7, DateUtil.setDateLong(-37), Transaction.TransactionType.RECHARGE, 3L));
		transactionRepository
				.save(new Transaction(87L, 100.7, DateUtil.setDateLong(-36), Transaction.TransactionType.RECHARGE, 4L));
		transactionRepository
				.save(new Transaction(88L, 105.7, DateUtil.setDateLong(-35), Transaction.TransactionType.RECHARGE, 5L));
		transactionRepository
				.save(new Transaction(89L, 300.8, DateUtil.setDateLong(-34), Transaction.TransactionType.RECHARGE, 6L));
		transactionRepository
				.save(new Transaction(90L, 310.8, DateUtil.setDateLong(-33), Transaction.TransactionType.RECHARGE, 7L));
		transactionRepository
				.save(new Transaction(91L, 280.8, DateUtil.setDateLong(-32), Transaction.TransactionType.RECHARGE, 8L));
		transactionRepository
				.save(new Transaction(92L, 300.7, DateUtil.setDateLong(-31), Transaction.TransactionType.RECHARGE, 9L));
		transactionRepository.save(
				new Transaction(93L, 310.7, DateUtil.setDateLong(-30), Transaction.TransactionType.RECHARGE, 10L));
		transactionRepository.save(
				new Transaction(94L, 280.7, DateUtil.setDateLong(-29), Transaction.TransactionType.RECHARGE, 11L));
		transactionRepository.save(
				new Transaction(95L, 100.7, DateUtil.setDateLong(-28), Transaction.TransactionType.RECHARGE, 12L));
		// rút tiền
		transactionRepository
				.save(new Transaction(96L, 50.0, DateUtil.setDateLong(-40), Transaction.TransactionType.WITHDRAW, 2L));
		transactionRepository
				.save(new Transaction(97L, 100.0, DateUtil.setDateLong(-39), Transaction.TransactionType.WITHDRAW, 3L));
		transactionRepository
				.save(new Transaction(98L, 120.0, DateUtil.setDateLong(-38), Transaction.TransactionType.WITHDRAW, 4L));
		transactionRepository
				.save(new Transaction(99L, 50.0, DateUtil.setDateLong(-37), Transaction.TransactionType.WITHDRAW, 5L));
		transactionRepository
				.save(new Transaction(100L, 60.0, DateUtil.setDateLong(-36), Transaction.TransactionType.WITHDRAW, 6L));
		transactionRepository
				.save(new Transaction(101L, 50.1, DateUtil.setDateLong(-35), Transaction.TransactionType.WITHDRAW, 7L));
		transactionRepository.save(
				new Transaction(102L, 100.1, DateUtil.setDateLong(-34), Transaction.TransactionType.WITHDRAW, 8L));
		transactionRepository.save(
				new Transaction(103L, 120.1, DateUtil.setDateLong(-33), Transaction.TransactionType.WITHDRAW, 9L));
		transactionRepository.save(
				new Transaction(104L, 50.1, DateUtil.setDateLong(-32), Transaction.TransactionType.WITHDRAW, 10L));
		transactionRepository.save(
				new Transaction(105L, 60.1, DateUtil.setDateLong(-31), Transaction.TransactionType.WITHDRAW, 11L));
		transactionRepository.save(
				new Transaction(106L, 50.2, DateUtil.setDateLong(-30), Transaction.TransactionType.WITHDRAW, 12L));
		transactionRepository.save(
				new Transaction(107L, 100.2, DateUtil.setDateLong(-29), Transaction.TransactionType.WITHDRAW, 13L));
		transactionRepository.save(
				new Transaction(108L, 120.2, DateUtil.setDateLong(-28), Transaction.TransactionType.WITHDRAW, 14L));
		transactionRepository.save(
				new Transaction(109L, 50.2, DateUtil.setDateLong(-27), Transaction.TransactionType.WITHDRAW, 15L));
		transactionRepository
				.save(new Transaction(110L, 60.2, DateUtil.setDateLong(-26), Transaction.TransactionType.WITHDRAW, 2L));
		transactionRepository
				.save(new Transaction(111L, 50.3, DateUtil.setDateLong(-25), Transaction.TransactionType.WITHDRAW, 3L));
		transactionRepository.save(
				new Transaction(112L, 100.3, DateUtil.setDateLong(-24), Transaction.TransactionType.WITHDRAW, 4L));
		transactionRepository.save(
				new Transaction(113L, 120.3, DateUtil.setDateLong(-23), Transaction.TransactionType.WITHDRAW, 5L));
		transactionRepository
				.save(new Transaction(114L, 50.3, DateUtil.setDateLong(-22), Transaction.TransactionType.WITHDRAW, 6L));
		transactionRepository
				.save(new Transaction(115L, 60.3, DateUtil.setDateLong(-21), Transaction.TransactionType.WITHDRAW, 7L));
		transactionRepository
				.save(new Transaction(116L, 50.4, DateUtil.setDateLong(-20), Transaction.TransactionType.WITHDRAW, 8L));
		transactionRepository.save(
				new Transaction(117L, 100.4, DateUtil.setDateLong(-19), Transaction.TransactionType.WITHDRAW, 9L));
		transactionRepository.save(
				new Transaction(118L, 120.4, DateUtil.setDateLong(-18), Transaction.TransactionType.WITHDRAW, 10L));
		transactionRepository.save(
				new Transaction(119L, 50.4, DateUtil.setDateLong(-17), Transaction.TransactionType.WITHDRAW, 11L));
		transactionRepository.save(
				new Transaction(120L, 60.4, DateUtil.setDateLong(-16), Transaction.TransactionType.WITHDRAW, 12L));
		transactionRepository.save(
				new Transaction(121L, 50.5, DateUtil.setDateLong(-15), Transaction.TransactionType.WITHDRAW, 13L));
		transactionRepository.save(
				new Transaction(122L, 100.5, DateUtil.setDateLong(-14), Transaction.TransactionType.WITHDRAW, 14L));
		transactionRepository.save(
				new Transaction(123L, 120.5, DateUtil.setDateLong(-13), Transaction.TransactionType.WITHDRAW, 15L));
		transactionRepository
				.save(new Transaction(124L, 50.5, DateUtil.setDateLong(-12), Transaction.TransactionType.WITHDRAW, 2L));
		transactionRepository
				.save(new Transaction(125L, 60.5, DateUtil.setDateLong(-11), Transaction.TransactionType.WITHDRAW, 3L));
		transactionRepository
				.save(new Transaction(126L, 50.6, DateUtil.setDateLong(-10), Transaction.TransactionType.WITHDRAW, 4L));
		transactionRepository
				.save(new Transaction(127L, 100.6, DateUtil.setDateLong(-9), Transaction.TransactionType.WITHDRAW, 5L));
		transactionRepository
				.save(new Transaction(128L, 120.6, DateUtil.setDateLong(-8), Transaction.TransactionType.WITHDRAW, 6L));
		transactionRepository
				.save(new Transaction(129L, 50.6, DateUtil.setDateLong(-7), Transaction.TransactionType.WITHDRAW, 7L));
		transactionRepository
				.save(new Transaction(130L, 60.6, DateUtil.setDateLong(-6), Transaction.TransactionType.WITHDRAW, 8L));
		transactionRepository
				.save(new Transaction(131L, 50.7, DateUtil.setDateLong(-5), Transaction.TransactionType.WITHDRAW, 9L));
		transactionRepository.save(
				new Transaction(132L, 100.7, DateUtil.setDateLong(-4), Transaction.TransactionType.WITHDRAW, 10L));
		transactionRepository.save(
				new Transaction(133L, 120.7, DateUtil.setDateLong(-3), Transaction.TransactionType.WITHDRAW, 11L));
		transactionRepository
				.save(new Transaction(134L, 50.7, DateUtil.setDateLong(-2), Transaction.TransactionType.WITHDRAW, 12L));
		transactionRepository
				.save(new Transaction(135L, 60.7, DateUtil.setDateLong(-1), Transaction.TransactionType.WITHDRAW, 13L));
		transactionRepository
				.save(new Transaction(136L, 50.8, DateUtil.setDateLong(0), Transaction.TransactionType.WITHDRAW, 14L));
		transactionRepository.save(
				new Transaction(137L, 100.8, DateUtil.setDateLong(-40), Transaction.TransactionType.WITHDRAW, 15L));
		transactionRepository.save(
				new Transaction(138L, 120.8, DateUtil.setDateLong(-39), Transaction.TransactionType.WITHDRAW, 2L));
		transactionRepository
				.save(new Transaction(139L, 50.8, DateUtil.setDateLong(-38), Transaction.TransactionType.WITHDRAW, 3L));
		transactionRepository
				.save(new Transaction(140L, 60.8, DateUtil.setDateLong(-37), Transaction.TransactionType.WITHDRAW, 4L));
		transactionRepository
				.save(new Transaction(141L, 50.9, DateUtil.setDateLong(-36), Transaction.TransactionType.WITHDRAW, 5L));
		transactionRepository.save(
				new Transaction(142L, 100.9, DateUtil.setDateLong(-35), Transaction.TransactionType.WITHDRAW, 6L));
		transactionRepository.save(
				new Transaction(143L, 120.9, DateUtil.setDateLong(-34), Transaction.TransactionType.WITHDRAW, 7L));
		transactionRepository
				.save(new Transaction(144L, 50.9, DateUtil.setDateLong(-33), Transaction.TransactionType.WITHDRAW, 8L));
		transactionRepository
				.save(new Transaction(145L, 60.9, DateUtil.setDateLong(-32), Transaction.TransactionType.WITHDRAW, 9L));
		transactionRepository.save(
				new Transaction(146L, 50.10, DateUtil.setDateLong(-31), Transaction.TransactionType.WITHDRAW, 10L));
		transactionRepository.save(
				new Transaction(147L, 100.10, DateUtil.setDateLong(-30), Transaction.TransactionType.WITHDRAW, 11L));
		transactionRepository.save(
				new Transaction(148L, 120.10, DateUtil.setDateLong(-29), Transaction.TransactionType.WITHDRAW, 12L));
		transactionRepository.save(
				new Transaction(149L, 50.10, DateUtil.setDateLong(-28), Transaction.TransactionType.WITHDRAW, 13L));
		transactionRepository.save(
				new Transaction(150L, 60.10, DateUtil.setDateLong(-27), Transaction.TransactionType.WITHDRAW, 14L));
		transactionRepository.save(
				new Transaction(151L, 50.11, DateUtil.setDateLong(-26), Transaction.TransactionType.WITHDRAW, 15L));
		transactionRepository.save(
				new Transaction(152L, 100.11, DateUtil.setDateLong(-25), Transaction.TransactionType.WITHDRAW, 2L));
		transactionRepository.save(
				new Transaction(153L, 120.11, DateUtil.setDateLong(-24), Transaction.TransactionType.WITHDRAW, 3L));
		transactionRepository.save(
				new Transaction(154L, 50.11, DateUtil.setDateLong(-23), Transaction.TransactionType.WITHDRAW, 4L));
		transactionRepository.save(
				new Transaction(155L, 60.11, DateUtil.setDateLong(-22), Transaction.TransactionType.WITHDRAW, 5L));
		transactionRepository.save(
				new Transaction(156L, 50.12, DateUtil.setDateLong(-21), Transaction.TransactionType.WITHDRAW, 6L));
		transactionRepository.save(
				new Transaction(157L, 100.12, DateUtil.setDateLong(-20), Transaction.TransactionType.WITHDRAW, 7L));
		transactionRepository.save(
				new Transaction(158L, 120.12, DateUtil.setDateLong(-19), Transaction.TransactionType.WITHDRAW, 8L));
		transactionRepository.save(
				new Transaction(159L, 50.12, DateUtil.setDateLong(-18), Transaction.TransactionType.WITHDRAW, 9L));
		transactionRepository.save(
				new Transaction(160L, 60.12, DateUtil.setDateLong(-17), Transaction.TransactionType.WITHDRAW, 10L));
		transactionRepository.save(
				new Transaction(161L, 50.13, DateUtil.setDateLong(-16), Transaction.TransactionType.WITHDRAW, 11L));
		transactionRepository.save(
				new Transaction(162L, 100.13, DateUtil.setDateLong(-15), Transaction.TransactionType.WITHDRAW, 12L));
		transactionRepository.save(
				new Transaction(163L, 120.13, DateUtil.setDateLong(-14), Transaction.TransactionType.WITHDRAW, 13L));
		transactionRepository.save(
				new Transaction(164L, 50.13, DateUtil.setDateLong(-13), Transaction.TransactionType.WITHDRAW, 14L));
		transactionRepository.save(
				new Transaction(165L, 60.13, DateUtil.setDateLong(-12), Transaction.TransactionType.WITHDRAW, 15L));
		transactionRepository.save(
				new Transaction(166L, 50.14, DateUtil.setDateLong(-11), Transaction.TransactionType.WITHDRAW, 2L));
		transactionRepository.save(
				new Transaction(167L, 100.14, DateUtil.setDateLong(-10), Transaction.TransactionType.WITHDRAW, 3L));
		transactionRepository.save(
				new Transaction(168L, 120.14, DateUtil.setDateLong(-9), Transaction.TransactionType.WITHDRAW, 4L));
		transactionRepository
				.save(new Transaction(169L, 50.14, DateUtil.setDateLong(-8), Transaction.TransactionType.WITHDRAW, 5L));
		transactionRepository
				.save(new Transaction(170L, 60.14, DateUtil.setDateLong(-7), Transaction.TransactionType.WITHDRAW, 6L));
		transactionRepository
				.save(new Transaction(171L, 50.15, DateUtil.setDateLong(-6), Transaction.TransactionType.WITHDRAW, 7L));
		transactionRepository.save(
				new Transaction(172L, 100.15, DateUtil.setDateLong(-5), Transaction.TransactionType.WITHDRAW, 8L));
		transactionRepository.save(
				new Transaction(173L, 120.15, DateUtil.setDateLong(-4), Transaction.TransactionType.WITHDRAW, 9L));
		transactionRepository.save(
				new Transaction(174L, 50.15, DateUtil.setDateLong(-3), Transaction.TransactionType.WITHDRAW, 10L));
		transactionRepository.save(
				new Transaction(175L, 60.15, DateUtil.setDateLong(-2), Transaction.TransactionType.WITHDRAW, 11L));
		transactionRepository.save(
				new Transaction(176L, 50.16, DateUtil.setDateLong(-1), Transaction.TransactionType.WITHDRAW, 12L));
		transactionRepository.save(
				new Transaction(177L, 100.16, DateUtil.setDateLong(0), Transaction.TransactionType.WITHDRAW, 13L));
		transactionRepository.save(
				new Transaction(178L, 120.16, DateUtil.setDateLong(-40), Transaction.TransactionType.WITHDRAW, 14L));
		transactionRepository.save(
				new Transaction(179L, 50.16, DateUtil.setDateLong(-39), Transaction.TransactionType.WITHDRAW, 15L));
		transactionRepository.save(
				new Transaction(180L, 60.16, DateUtil.setDateLong(-38), Transaction.TransactionType.WITHDRAW, 2L));
		transactionRepository.save(
				new Transaction(181L, 50.17, DateUtil.setDateLong(-37), Transaction.TransactionType.WITHDRAW, 3L));
		transactionRepository.save(
				new Transaction(182L, 100.17, DateUtil.setDateLong(-36), Transaction.TransactionType.WITHDRAW, 4L));
		transactionRepository.save(
				new Transaction(183L, 120.17, DateUtil.setDateLong(-35), Transaction.TransactionType.WITHDRAW, 5L));
		transactionRepository.save(
				new Transaction(184L, 50.17, DateUtil.setDateLong(-34), Transaction.TransactionType.WITHDRAW, 6L));
		transactionRepository.save(
				new Transaction(185L, 60.17, DateUtil.setDateLong(-33), Transaction.TransactionType.WITHDRAW, 7L));
		transactionRepository.save(
				new Transaction(186L, 50.18, DateUtil.setDateLong(-32), Transaction.TransactionType.WITHDRAW, 8L));
		transactionRepository.save(
				new Transaction(187L, 100.18, DateUtil.setDateLong(-31), Transaction.TransactionType.WITHDRAW, 9L));
		transactionRepository.save(
				new Transaction(188L, 120.18, DateUtil.setDateLong(-30), Transaction.TransactionType.WITHDRAW, 10L));
		transactionRepository.save(
				new Transaction(189L, 50.18, DateUtil.setDateLong(-29), Transaction.TransactionType.WITHDRAW, 11L));
		transactionRepository.save(
				new Transaction(190L, 60.18, DateUtil.setDateLong(-28), Transaction.TransactionType.WITHDRAW, 12L));

		// tiền lương
		transactionRepository.save(
				new Transaction(191L, 100.0, DateUtil.setDateLong(-40), Transaction.TransactionType.WAGE, 19L, 2L));
		transactionRepository.save(
				new Transaction(192L, 100.0, DateUtil.setDateLong(-39), Transaction.TransactionType.WAGE, 18L, 3L));
		transactionRepository.save(
				new Transaction(193L, 120.0, DateUtil.setDateLong(-38), Transaction.TransactionType.WAGE, 17L, 4L));
		transactionRepository.save(
				new Transaction(194L, 150.0, DateUtil.setDateLong(-37), Transaction.TransactionType.WAGE, 16L, 5L));
		transactionRepository.save(
				new Transaction(195L, 160.0, DateUtil.setDateLong(-36), Transaction.TransactionType.WAGE, 15L, 6L));
		transactionRepository.save(
				new Transaction(196L, 150.1, DateUtil.setDateLong(-35), Transaction.TransactionType.WAGE, 14L, 7L));
		transactionRepository.save(
				new Transaction(197L, 200.1, DateUtil.setDateLong(-34), Transaction.TransactionType.WAGE, 13L, 8L));
		transactionRepository.save(
				new Transaction(198L, 100.0, DateUtil.setDateLong(-33), Transaction.TransactionType.WAGE, 12L, 9L));
		transactionRepository.save(
				new Transaction(199L, 100.0, DateUtil.setDateLong(-32), Transaction.TransactionType.WAGE, 11L, 10L));
		transactionRepository.save(
				new Transaction(200L, 120.1, DateUtil.setDateLong(-31), Transaction.TransactionType.WAGE, 10L, 11L));
		transactionRepository.save(
				new Transaction(201L, 150.1, DateUtil.setDateLong(-30), Transaction.TransactionType.WAGE, 9L, 12L));
		transactionRepository.save(
				new Transaction(202L, 160.1, DateUtil.setDateLong(-29), Transaction.TransactionType.WAGE, 8L, 13L));
		transactionRepository.save(
				new Transaction(203L, 150.2, DateUtil.setDateLong(-28), Transaction.TransactionType.WAGE, 7L, 14L));
		transactionRepository.save(
				new Transaction(204L, 200.2, DateUtil.setDateLong(-27), Transaction.TransactionType.WAGE, 6L, 15L));
		transactionRepository.save(
				new Transaction(205L, 100.0, DateUtil.setDateLong(-26), Transaction.TransactionType.WAGE, 5L, 2L));
		transactionRepository.save(
				new Transaction(206L, 100.0, DateUtil.setDateLong(-25), Transaction.TransactionType.WAGE, 4L, 3L));
		transactionRepository.save(
				new Transaction(207L, 120.2, DateUtil.setDateLong(-24), Transaction.TransactionType.WAGE, 3L, 4L));
		transactionRepository.save(
				new Transaction(208L, 150.2, DateUtil.setDateLong(-23), Transaction.TransactionType.WAGE, 2L, 5L));
		transactionRepository.save(
				new Transaction(209L, 160.2, DateUtil.setDateLong(-22), Transaction.TransactionType.WAGE, 1L, 6L));

	}
}
