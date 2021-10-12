package com.freelancer.repository;

import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.Job;
import com.freelancer.model.OtherSkill;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    @Query(value = "SELECT a FROM Job a WHERE (UPPER(a.name) like UPPER(CONCAT('%', :keysearch,'%'))) "
            + "ORDER BY a.id")
    List<Job> searchJob(@Param("keysearch") String keysearch, Pageable pageable);

    @Query(value = "SELECT count(a) FROM Job a WHERE (UPPER(a.name) like UPPER(CONCAT('%', :keysearch,'%')))")
    Long countJob(@Param("keysearch") String keysearch);

}
