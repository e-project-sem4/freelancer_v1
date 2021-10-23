package com.freelancer.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.freelancer.model.Job;

public class JobSpecification implements Specification<Job> {
	private static final long serialVersionUID = 1L;
	private SearchCriteria searchCriteria;

	public JobSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public JobSpecification() {

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

		case "==skill":
			Join<Object, Object> jobOtherSkillJoin = root.join("otherSkills");
			return criteriaBuilder.equal(jobOtherSkillJoin.get(searchCriteria.getKey()), searchCriteria.getValue());
		default:
			return null;

		}
	}
}
