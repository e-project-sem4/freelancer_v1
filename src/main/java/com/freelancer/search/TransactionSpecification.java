package com.freelancer.search;

import com.freelancer.model.Job;
import com.freelancer.model.Transaction;
import com.freelancer.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class TransactionSpecification implements Specification<Transaction> {
    private static final long serialVersionUID = 1L;
    private SearchCriteria searchCriteria;

    public TransactionSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public TransactionSpecification() {

    }

    @Override
    public Specification<Transaction> and(Specification<Transaction> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Transaction> or(Specification<Transaction> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Transaction> root, // BẢNG NÀO
                                 CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        if (this.searchCriteria.getKey() == null) {
            return null;
        }

        switch (this.searchCriteria.getOperation()) {

            case "==":
                return criteriaBuilder.equal(root.get(this.searchCriteria.getKey()), searchCriteria.getValue());
            case "like":
                return criteriaBuilder.like(root.get(searchCriteria.getKey()),
                        "%" + searchCriteria.getValue().toString() + "%");
            case ">=":
                return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()),
                        searchCriteria.getValue().toString());
            case "<=":
                return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()),
                        searchCriteria.getValue().toString());

            case "likeUsername":
                Join<Transaction, User> transactionUser = root.join("user");
                return criteriaBuilder.equal(transactionUser.get(searchCriteria.getKey()),
                        searchCriteria.getValue().toString());
            case "likeJobname":
                Join<Transaction, Job> transactionJob = root.join("job");
                return criteriaBuilder.like(transactionJob.get(searchCriteria.getKey()),
                        "%" + searchCriteria.getValue().toString() + "%");
            default:
                return null;

        }
    }
}
