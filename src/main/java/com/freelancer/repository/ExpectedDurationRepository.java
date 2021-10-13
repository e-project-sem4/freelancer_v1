package com.freelancer.repository;

import com.freelancer.model.Complexity;
import com.freelancer.model.ExpectedDuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpectedDurationRepository extends JpaRepository<ExpectedDuration, Long> , JpaSpecificationExecutor<ExpectedDuration> {
    @Query(value = "SELECT a FROM ExpectedDuration a WHERE (UPPER(a.durationText) like UPPER(CONCAT('%', :keysearch,'%'))) "
            + "ORDER BY a.id")
    List<ExpectedDuration> searchObj(@Param("keysearch") String keysearch, Pageable pageable);

    @Query(value = "SELECT count(a) FROM ExpectedDuration a WHERE (UPPER(a.durationText) like UPPER(CONCAT('%', :keysearch,'%')))")
    Long countObj(@Param("keysearch") String keysearch);
}
