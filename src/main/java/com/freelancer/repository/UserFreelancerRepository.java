package com.freelancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freelancer.model.UserFreelancer;

@Repository
public interface UserFreelancerRepository extends JpaRepository<UserFreelancer, Long>, JpaSpecificationExecutor<UserFreelancer> {
	@Query("SELECT a FROM UserFreelancer a WHERE user_account_id = :id")
	UserFreelancer getFreelancerByUserAccountId(@Param("id") Long id);
	
}
