package com.freelancer.repository;

import com.freelancer.model.UserFreelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFreelancerRepository extends JpaRepository<UserFreelancer, Long> {
}
