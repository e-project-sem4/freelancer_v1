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
    @Query(value = "SELECT sum(round(price)) sum, MONTH(date(FROM_UNIXTIME(createAt/1000))) month " +
            "FROM transactions where type = 3 GROUP BY month"
            ,nativeQuery=true)
    public List<Object[]> finMonth();
    @Query(value = "SELECT sum(round(price)) sum, date(FROM_UNIXTIME(createAt/1000)) day " +
            "from transactions where month(date(FROM_UNIXTIME(createAt/1000))) AND type = 3 GROUP BY day"
            ,nativeQuery=true)
    public List<Object[]> finDay();
}