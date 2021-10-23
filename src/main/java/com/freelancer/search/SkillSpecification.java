package com.freelancer.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.freelancer.model.Skill;

public class SkillSpecification implements Specification<Skill>{
	private static final long serialVersionUID = 1L;
	private SearchCriteria searchCriteria;


    public SkillSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public SkillSpecification()  {

    }

    @Override
    public Specification<Skill> and(Specification<Skill> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Skill> or(Specification<Skill> other) {
        return Specification.super.or(other);
    }


    @Override
    public Predicate toPredicate(Root<Skill> root, // BẢNG NÀO
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
