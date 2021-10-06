package com.freelancer.repository.job;

import com.freelancer.model.job.Job;
import com.freelancer.model.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
