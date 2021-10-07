package com.freelancer.repository;

import com.freelancer.model.ProposalStatusCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalStatusCatalogRepository extends JpaRepository<ProposalStatusCatalog, Long> {
}
