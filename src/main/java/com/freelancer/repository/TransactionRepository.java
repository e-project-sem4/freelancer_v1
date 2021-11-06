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
            "from transactions where createAt BETWEEN :start AND :end AND type = 3 GROUP BY day"
            ,nativeQuery=true)
    public List<Object[]> finDay(@Param("start") Long start, @Param("end")Long end);

    @Query(value = "SELECT sum(round(price)) sum, date(FROM_UNIXTIME(createAt/1000)) day, type FROM `transactions` where createAt BETWEEN :start AND :end GROUP BY day, type ORDER BY `day` ASC",nativeQuery=true)
    public List<Object[]> findAllMultipleChartJs(@Param("start") Long start, @Param("end") Long end);

    public List<Transaction> findAllByType(Transaction.TransactionType type);
    @Query("SELECT a FROM Transaction a WHERE a.user_account_id = :accountId")
    public List<Transaction> findAllByUser(@Param("accountId") Long accountId);
}

//// postgresql
//public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
//    @Query(value = "SELECT sum(round(price)) as sum, EXTRACT(MONTH FROM date(to_timestamp(create_at/1000))) as month FROM transactions where type = 3 GROUP BY month"
//            ,nativeQuery=true)
//    public List<Object[]> finMonth();
//    @Query(value = "SELECT sum(round(price)) as sum, date(to_timestamp(create_at/1000)) as day " +
//            "from transactions where type = 3 GROUP BY day"
//            ,nativeQuery=true)
//    public List<Object[]> finDay();
//@Query(value = "SELECT sum(round(price)) as sum, date(FROM_UNIXTIME(create_at/1000)) as day, type FROM transactions where create_at BETWEEN :start AND :end GROUP BY day, type ORDER BY day ASC",nativeQuery=true)
//public List<Object[]> findAllMultipleChartJs(@Param("start") Long start, @Param("end") Long end);
//}