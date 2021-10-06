package com.freelancer.repository.proposalAndContract;

import com.freelancer.model.proposalAndContract.Proposal;
import com.freelancer.model.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
}
