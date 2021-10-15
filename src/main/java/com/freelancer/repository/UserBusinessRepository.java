package com.freelancer.repository;

import com.freelancer.model.UserBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBusinessRepository extends JpaRepository<UserBusiness, Long> {
	
	@Query("SELECT a FROM UserBusiness a WHERE user_account_id = :id")
	UserBusiness getBusinessByUserAccountId(@Param("id") Long id);
}
