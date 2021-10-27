package com.freelancer.repository;

import com.freelancer.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    @Query(value = "SELECT count(ac) as count,sum(ac.price) as total, ac.createAt as date FROM Transaction ac " +
            "WHERE ac.createAt BETWEEN :startDate AND :endDate GROUP BY ac.type")
    public List<Map<Long,Transaction>> findRegisteredCustomersHistory(@Param("startDate") Long startDate, @Param("endDate") Long endDate);
}
