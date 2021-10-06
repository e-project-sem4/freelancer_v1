package com.freelancer.repository.freelancer;

import com.freelancer.model.freelance.HasSkill;
import com.freelancer.model.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HasSkillRepository extends JpaRepository<HasSkill, Long> {
}
