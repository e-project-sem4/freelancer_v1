package com.freelancer.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.freelancer.model.UserFreelancer;

public class FreelancerSpecification implements Specification<UserFreelancer> {
	private static final long serialVersionUID = 1L;
	private SearchCriteria searchCriteria;

	public FreelancerSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public FreelancerSpecification() {
	}

	@Override
	public Specification<UserFreelancer> and(Specification<UserFreelancer> other) {
		return Specification.super.and(other);
	}

	@Override
	public Specification<UserFreelancer> or(Specification<UserFreelancer> other) {
		return Specification.super.or(other);
	}

	@Override
	public Predicate toPredicate(Root<UserFreelancer> root, CriteriaQuery<?> criteriaQuery,
			CriteriaBuilder criteriaBuilder) {
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
			Join<Object, Object> freelancerHaSkillJoin = root.join("hasSkills");
			return criteriaBuilder.equal(freelancerHaSkillJoin.get(searchCriteria.getKey()), searchCriteria.getValue());
		case "==status":
			Join<Object, Object> freelancerJoinAccount = root.join("user_account_id");
			return criteriaBuilder.equal(freelancerJoinAccount.get(searchCriteria.getKey()), searchCriteria.getValue());
		default:
			return null;
		}
	}
}