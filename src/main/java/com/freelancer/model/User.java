package com.freelancer.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_account")
@ToString
public class User {
	// vào package database, mở class Database, mở comment ra rồi chạy project để
	// tạo tài khoản demo.
	// CHạy xong thì comment lại để tránh lỗi
	// Đọc log để lấy token
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
	@Column(unique = true, nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@Size(min = 8, message = "Minimum password length: 8 characters")
	private String password;

	@Size(max = 12, message = "Max phone length: 12 characters")
	private String phone;

	@Size(min = 4, max = 100, message = "Minimum full name max length: 100 characters")
	@Column(nullable = false)
	private String fullName;

	@ElementCollection(fetch = FetchType.EAGER)
	List<Role> roles;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private UserFreelancer userFreelancers;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private UserBusiness userBusinesses;

	public User(Long id, String username, String email, String password, String phone, String fullName,
			List<Role> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.fullName = fullName;
		this.roles = roles;
	}
}
