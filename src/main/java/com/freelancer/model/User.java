package com.freelancer.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_account")
public class User {
// vào package database, mở class Database, mở comment ra rồi chạy project để tạo tài khoản demo.
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

	@Size(min = 4,max = 100, message = "Minimum full name max length: 100 characters")
	private String full_name;

	@ElementCollection(fetch = FetchType.EAGER)
	List<Role> roles;


	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Collection<UserFreelancer> userFreelancers;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Collection<UserBusiness> userBusinesses;

	public User(Long id, String username, String email, String password, String phone, String full_name, List<Role> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.full_name = full_name;
		this.roles = roles;
	}
}
