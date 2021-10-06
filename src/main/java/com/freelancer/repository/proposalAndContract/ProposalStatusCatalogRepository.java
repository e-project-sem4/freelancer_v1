package com.freelancer.repository.proposalAndContract;

import com.freelancer.model.proposalAndContract.ProposalStatusCatalog;
import com.freelancer.model.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalStatusCatalogRepository extends JpaRepository<ProposalStatusCatalog, Long> {
}
