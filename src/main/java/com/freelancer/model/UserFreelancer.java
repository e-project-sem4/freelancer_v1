package com.freelancer.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_freelancer")
public class UserFreelancer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long user_account_id;
	private long registrationDate;
	@Size(max = 255, message = "Max location length: 255 characters")
	@Column(nullable = false)
	private String location;
	@Size(max = 1000, message = "Max location length: 1000 characters")
	@Column(nullable = false)
	private String overview;
	@Size(max = 1000, message = "Max location length: 1000 characters")
	@Column(nullable = false)
	private String certifications;

	@JsonBackReference(value = "user")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_account_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

	@JsonBackReference(value = "hasSkills")
	@OneToMany(mappedBy = "userFreelancer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<HasSkill> hasSkills;

	@JsonBackReference(value = "messages")
	@OneToMany(mappedBy = "userFreelancer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Message> messages;

	@JsonBackReference(value = "proposals")
	@OneToMany(mappedBy = "userFreelancer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Proposal> proposals;

	public UserFreelancer(Long id, Long user_account_id, long registrationDate, String location, String overview,
			String certifications) {
		this.id = id;
		this.user_account_id = user_account_id;
		this.registrationDate = registrationDate;
		this.location = location;
		this.overview = overview;
		this.certifications = certifications;
	}
}
