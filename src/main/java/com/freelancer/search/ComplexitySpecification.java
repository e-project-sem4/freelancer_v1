package com.freelancer.search;

import com.freelancer.model.Complexity;
import com.freelancer.model.Job;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ComplexitySpecification implements Specification<Complexity>{
    private SearchCriteria searchCriteria;


    public ComplexitySpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public ComplexitySpecification()  {

    }

    @Override
    public Specification<Complexity> and(Specification<Complexity> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Complexity> or(Specification<Complexity> other) {
        return Specification.super.or(other);
    }


    @Override
    public Predicate toPredicate(Root<Complexity> root, // BẢNG NÀO
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
