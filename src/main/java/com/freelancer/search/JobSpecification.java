package com.freelancer.search;

import com.freelancer.model.Complexity;
import com.freelancer.model.Job;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

public class JobSpecification implements Specification<Job>{
    private SearchCriteria searchCriteria;


    public JobSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public JobSpecification()  {

    }

    @Override
    public Specification<Job> and(Specification<Job> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Job> or(Specification<Job> other) {
        return Specification.super.or(other);
    }


    @Override
    public Predicate toPredicate(Root<Job> root, // BẢNG NÀO
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {


        Join<Job, Complexity> job_complexity = root.join("complexity");

        if (this.searchCriteria.getKey() == null) {
            return null;
        }

        switch (this.searchCriteria.getOperation()){
            case "==":
                return criteriaBuilder.equal(job_complexity.get(this.searchCriteria.getKey()),searchCriteria.getValue());
        }


//        if (this.searchCriteria.getOperation().equals("like")) {
//            return criteriaBuilder.like(root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue().toString() + "%");
//        } else if (this.searchCriteria.getOperation().equals("==")) {
//            return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
//        } else if (this.searchCriteria.getOperation().equals(">=")) {
//            return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
//        }
//        else if (this.searchCriteria.getOperation().equals("<=")) {
//            return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
//        }
        return null;
    }
}
