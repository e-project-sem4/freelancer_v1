package com.freelancer.repository;

import com.freelancer.model.UserBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBusinessRepository extends JpaRepository<UserBusiness, Long> {
}
