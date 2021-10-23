package com.freelancer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.freelancer.model.Proposal;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> , JpaSpecificationExecutor<Proposal> {

    @Query("select p From Proposal p where p.job_id = :id AND (p.proposal_status_catalog_id=1 OR p.proposal_status_catalog_id=2 OR p.proposal_status_catalog_id=3)")
    List<Proposal> findAllByJob_id(Long id);
}
