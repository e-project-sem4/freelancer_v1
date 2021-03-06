package com.freelancer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.freelancer.model.Job;


@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
//    @Query(value = "SELECT a FROM Job a WHERE (UPPER(a.name) like UPPER(CONCAT('%', :keysearch,'%')))"
//            + "ORDER BY a.id")
//    List<Job> searchJob(@Param("keysearch") String keysearch, Pageable pageable);
//
//    @Query(value = "SELECT count(a) FROM Job a WHERE (UPPER(a.name) like UPPER(CONCAT('%', :keysearch,'%')))")
//    Long countJob(@Param("keysearch") String keysearch);

//    @Query("SELECT j FROM Job j ")
//    List<Job> searchHasPage(Specification<Job> specification, Pageable pageable);
//
//    @Query("SELECT j FROM Job j ")
//    List<Job> searchNoPage(Specification<Job> specification);
    @Query("SELECT j FROM Job j WHERE j.user_business_id=:id")
    List<Job> findAllByUser_business_id(Long id);


}
