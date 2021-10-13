package com.freelancer.repository;

import java.util.List;

import com.freelancer.model.Job;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freelancer.model.Complexity;

@Repository
public interface ComplexityRepository extends JpaRepository<Complexity, Long> , JpaSpecificationExecutor<Complexity> {


}
