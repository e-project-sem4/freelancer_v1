package com.freelancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freelancer.model.HasSkill;

@Repository
public interface HasSkillRepository extends JpaRepository<HasSkill, Long>, JpaSpecificationExecutor<HasSkill> {

	@Modifying
	@Query("DELETE FROM HasSkill a WHERE a.user_freelancer_id = :freelancerId")
	void deleteSkills(@Param("freelancerId") Long id);
}
