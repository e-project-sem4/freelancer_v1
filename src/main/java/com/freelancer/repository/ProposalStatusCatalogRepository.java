package com.freelancer.repository;

import com.freelancer.model.Complexity;
import com.freelancer.model.ProposalStatusCatalog;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalStatusCatalogRepository extends JpaRepository<ProposalStatusCatalog, Long> , JpaSpecificationExecutor<ProposalStatusCatalog> {
    @Query(value = "SELECT a FROM ProposalStatusCatalog a WHERE (UPPER(a.statusName) like UPPER(CONCAT('%', :keysearch,'%'))) "
            + "ORDER BY a.id")
    List<ProposalStatusCatalog> searchObj(@Param("keysearch") String keysearch, Pageable pageable);

    @Query(value = "SELECT count(a) FROM ProposalStatusCatalog a WHERE (UPPER(a.statusName) like UPPER(CONCAT('%', :keysearch,'%')))")
    Long countObj(@Param("keysearch") String keysearch);
}
