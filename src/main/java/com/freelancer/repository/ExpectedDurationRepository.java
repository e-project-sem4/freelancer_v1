package com.freelancer.repository;

import com.freelancer.model.ExpectedDuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpectedDurationRepository extends JpaRepository<ExpectedDuration, Long> {
}
