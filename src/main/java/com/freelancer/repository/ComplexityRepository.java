package com.freelancer.repository;

import com.freelancer.model.Complexity;
import com.freelancer.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplexityRepository extends JpaRepository<Complexity, Long> {

    @Query(value = "SELECT a FROM Complexity a WHERE (UPPER(a.complexityText) like UPPER(CONCAT('%', :keysearch,'%'))) "
            + "ORDER BY a.id")
    List<Complexity> searchComplexity(@Param("keysearch") String keysearch, Pageable pageable);

    @Query(value = "SELECT count(a) FROM Complexity a WHERE (UPPER(a.complexityText) like UPPER(CONCAT('%', :keysearch,'%'))) "
            + "ORDER BY a.id")
    Long countComplexity(@Param("keysearch") String keysearch);
}
