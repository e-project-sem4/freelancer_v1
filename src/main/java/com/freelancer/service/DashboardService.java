package com.freelancer.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freelancer.repository.UserBusinessRepository;
import com.freelancer.repository.UserFreelancerRepository;
import com.freelancer.repository.UserRepository;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBusinessRepository userBusinessRepository;

    @Autowired
    private UserFreelancerRepository userFreelancerRepository;

    public HashMap<Integer, Long> getCountDashboard() {
        HashMap<Integer, Long> count = new HashMap<Integer, Long>();
        Long countUser = userRepository.count();
        Long countFreelancer = userFreelancerRepository.count();
        Long countBusiness = userBusinessRepository.count();
        count.put(1,countUser);
        count.put(2,countFreelancer);
        count.put(3,countBusiness);
        return count;
    }
}
