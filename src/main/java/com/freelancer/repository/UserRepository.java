package com.freelancer.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.freelancer.model.User;

public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {

	boolean existsByUsername(String username);

	User findByUsername(String username);
	@Transactional
	void deleteByUsername(String username);

	@Query(value = "SELECT a FROM User a WHERE (UPPER(a.username) like UPPER(CONCAT('%', :keysearch,'%')) "
			+ "OR UPPER(a.fullName) like UPPER(CONCAT('%', :keysearch,'%'))) and username != :username ORDER BY a.username")
	List<User> searchUser(@Param("keysearch") String keysearch, @Param("username") String username, Pageable pageable);

	@Query(value = "SELECT count(a) FROM User a WHERE (UPPER(a.username) like UPPER(CONCAT('%', :keysearch,'%')) "
			+ "OR UPPER(a.fullName) like UPPER(CONCAT('%', :keysearch,'%'))) and username != :username")
	Long countUser(@Param("keysearch") String keysearch, @Param("username") String username);

	@Modifying
	@Query(value = "UPDATE User a SET a.password = :newPassword WHERE a.username = :username")
	void changePasswordByUsername(@Param("newPassword") String newPassword, @Param("username") String username);

	@Modifying
	@Query(value = "UPDATE User a SET a.fullName = :fullName, a.email = :email, a.phone = :phone WHERE a.username = :username")
	Integer updateProfileByUsername(@Param("fullName") String fullName, @Param("email") String email,
			@Param("phone") String phone, @Param("username") String username);
	
	@Query("SELECT a.id FROM User a WHERE a.username = :username")
	Long getIdByUsername(@Param("username") String username);
}
