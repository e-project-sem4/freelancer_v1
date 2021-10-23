package com.freelancer.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.freelancer.model.Contract;

public class ContractSpecification implements Specification<Contract> {
	private static final long serialVersionUID = 1L;
	private SearchCriteria searchCriteria;

	public ContractSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public ContractSpecification() {

	}

	@Override
	public Specification<Contract> and(Specification<Contract> other) {
		return Specification.super.and(other);
	}

	@Override
	public Specification<Contract> or(Specification<Contract> other) {
		return Specification.super.or(other);
	}

	@Override
	public Predicate toPredicate(Root<Contract> root, // BẢNG NÀO
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
		default:
			return null;

		}
	}
}
