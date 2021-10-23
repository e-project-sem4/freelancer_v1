package com.freelancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.freelancer.model.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> , JpaSpecificationExecutor<Contract> {
}
