package com.freelancer.repository;

import com.freelancer.model.Complexity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplexityRepository extends JpaRepository<Complexity, Long> {
}
