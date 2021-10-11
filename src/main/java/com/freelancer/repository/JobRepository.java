package com.freelancer.repository;

import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.Job;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query(value = "SELECT a.id, a.name, a.description,a.paymentAmount, a.complexity_id, a.expected_duration_id," +
            "a.skill_id, a.user_business_id FROM Job a WHERE (UPPER(a.name) like UPPER(CONCAT('%', :keysearch,'%'))) "
            + "ORDER BY a.id",nativeQuery = false)
    List<Job> searchJob(@Param("keysearch") String keysearch, Pageable pageable);

    @Query(value = "SELECT count(a.id) FROM Job a WHERE (UPPER(a.name) like UPPER(CONCAT('%', :keysearch,'%'))) "
            + "ORDER BY a.id",nativeQuery = false)
    Long countJob(@Param("keysearch") String keysearch);

}
