package com.freelancer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freelancer.model.ChatKeyUser;

@Repository
public interface ChatKeyUserRepository extends JpaRepository<ChatKeyUser, Long> {

	@Query("SELECT a FROM ChatKeyUser a WHERE a.senderId IS NULL OR a.senderId = :id "
			+ "OR a.senderId IS NULL OR a.senderId = :id2")
	List<ChatKeyUser> findChatKey(@Param("id") Long id, @Param("id2") Long id2);

}
