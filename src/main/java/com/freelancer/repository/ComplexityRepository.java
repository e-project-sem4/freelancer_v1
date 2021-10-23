package com.freelancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.freelancer.model.Complexity;

@Repository
public interface ComplexityRepository extends JpaRepository<Complexity, Long> , JpaSpecificationExecutor<Complexity> {


}
