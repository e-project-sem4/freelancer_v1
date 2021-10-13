package com.freelancer.search;

import com.freelancer.model.Complexity;
import com.freelancer.model.ExpectedDuration;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DurationSpecification implements Specification<ExpectedDuration>{
    private SearchCriteria searchCriteria;


    public DurationSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public DurationSpecification()  {

    }

    @Override
    public Specification<ExpectedDuration> and(Specification<ExpectedDuration> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<ExpectedDuration> or(Specification<ExpectedDuration> other) {
        return Specification.super.or(other);
    }


    @Override
    public Predicate toPredicate(Root<ExpectedDuration> root, // BẢNG NÀO
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {

        if (this.searchCriteria.getKey() == null) {
            return null;
        }

        switch (this.searchCriteria.getOperation()){

            case "==":
                return criteriaBuilder.equal(root.get(this.searchCriteria.getKey()),searchCriteria.getValue());
            case "like":
                return criteriaBuilder.like(root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue().toString() + "%");
            case ">=":
                return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case "<=":
                return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            default:return null;

        }
    }
}