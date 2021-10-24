package com.freelancer.service;

import java.util.HashMap;

import com.freelancer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserBusinessRepository userBusinessRepository;

	@Autowired
	private UserFreelancerRepository userFreelancerRepository;

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private OtherSkillRepository otherSkillRepository;

	public HashMap<Integer, Long> getCountDashboard() {
		HashMap<Integer, Long> count = new HashMap<Integer, Long>();
		Long countUser = userRepository.count();
		Long countFreelancer = userFreelancerRepository.count();
		Long countBusiness = userBusinessRepository.count();
		Long countJob = jobRepository.count();
		count.put(1, countUser);
		count.put(2, countFreelancer);
		count.put(3, countBusiness);
		count.put(4, countJob);
		return count;
	}
}
