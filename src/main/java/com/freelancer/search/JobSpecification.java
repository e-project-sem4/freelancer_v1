package com.freelancer.search;

import com.freelancer.model.Job;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class JobSpecification implements Specification<Job> {
    private  SearchCriteria searchCriteria;

    @Override
    public Specification<Job> and(Specification<Job> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Job> or(Specification<Job> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Job> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get(searchCriteria.getKey()),searchCriteria.getValue().toString());
    }
}
