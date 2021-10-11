package com.freelancer.repository;

import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.Job;
import com.freelancer.model.OtherSkill;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    @Query(value = "SELECT a, b FROM Job a JOIN OtherSkill b On a.id = b.job_id WHERE (UPPER(a.name) like UPPER(CONCAT('%', :keysearch,'%'))) "
            + "ORDER BY a.id")
    List<Job> searchJob(@Param("keysearch") String keysearch, Pageable pageable);

    @Query(value = "SELECT count(a) FROM Job a WHERE (UPPER(a.name) like UPPER(CONCAT('%', :keysearch,'%')))")
    Long countJob(@Param("keysearch") String keysearch);

}
