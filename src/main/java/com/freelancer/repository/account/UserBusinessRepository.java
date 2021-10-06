package com.freelancer.repository.account;

import com.freelancer.model.account.UserBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBusinessRepository extends JpaRepository<UserBusiness, Long> {
}
