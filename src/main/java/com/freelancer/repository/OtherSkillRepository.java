package com.freelancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.freelancer.model.OtherSkill;

@Repository
public interface OtherSkillRepository extends JpaRepository<OtherSkill, Long> , JpaSpecificationExecutor<OtherSkill> {
}
