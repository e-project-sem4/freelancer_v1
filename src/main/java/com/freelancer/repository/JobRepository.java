package com.freelancer.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freelancer.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query(value = "SELECT a FROM Job a WHERE (UPPER(a.name) like UPPER(CONCAT('%', :keysearch,'%'))) "
            + "ORDER BY a.id")
    List<Job> searchJob(@Param("keysearch") String keysearch, Pageable pageable);

    @Query(value = "SELECT count(a) FROM Job a WHERE (UPPER(a.name) like UPPER(CONCAT('%', :keysearch,'%')))")
    Long countJob(@Param("keysearch") String keysearch);

}
