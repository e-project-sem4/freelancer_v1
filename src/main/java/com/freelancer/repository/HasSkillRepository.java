package com.freelancer.repository;

import com.freelancer.model.HasSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HasSkillRepository extends JpaRepository<HasSkill, Long> {
}
