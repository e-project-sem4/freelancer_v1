package com.freelancer.repository.job;

import com.freelancer.model.job.ExpectedDuration;
import com.freelancer.model.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpectedDurationRepository extends JpaRepository<ExpectedDuration, Long> {
}
