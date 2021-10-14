package com.freelancer.repository;

import com.freelancer.model.Complexity;
import com.freelancer.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> , JpaSpecificationExecutor<Proposal> {
}
