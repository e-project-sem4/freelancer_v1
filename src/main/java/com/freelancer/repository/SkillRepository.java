package com.freelancer.repository;

import com.freelancer.model.Complexity;
import com.freelancer.model.Skill;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> , JpaSpecificationExecutor<Skill> {
    @Query(value = "SELECT a FROM Skill a WHERE (UPPER(a.skillName) like UPPER(CONCAT('%', :keysearch,'%'))) "
            + "ORDER BY a.id")
    List<Skill> searchObj(@Param("keysearch") String keysearch, Pageable pageable);

    @Query(value = "SELECT count(a) FROM Skill a WHERE (UPPER(a.skillName) like UPPER(CONCAT('%', :keysearch,'%')))")
    Long countObj(@Param("keysearch") String keysearch);
}
