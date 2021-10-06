package com.freelancer.repository.skill;

import com.freelancer.model.account.User;
import com.freelancer.model.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
}
