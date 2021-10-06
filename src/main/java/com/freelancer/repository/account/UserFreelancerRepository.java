package com.freelancer.repository.account;

import com.freelancer.model.account.UserFreelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFreelancerRepository extends JpaRepository<UserFreelancer, Long> {
}
