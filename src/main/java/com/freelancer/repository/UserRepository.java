package com.freelancer.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.freelancer.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByUsername(String username);

	User findByUsername(String username);

	@Transactional
	void deleteByUsername(String username);
	
	@Query(value = "SELECT a FROM User a WHERE (UPPER(a.username) like UPPER(CONCAT('%', :keysearch,'%')) "
			+ "OR UPPER(a.username) like UPPER(CONCAT('%', :keysearch,'%'))) and id != :id ORDER BY a.username")
	List<User> searchUser(@Param("keysearch") String keysearch, @Param("id") Long id, Pageable pageable);
	
	@Query(value = "SELECT count(a) FROM User a WHERE (UPPER(a.username) like UPPER(CONCAT('%', :keysearch,'%')) "
			+ "OR UPPER(a.username) like UPPER(CONCAT('%', :keysearch,'%'))) and id != :id ORDER BY a.username")
	Long countUser(@Param("keysearch") String keysearch, @Param("id") Long id);

}
