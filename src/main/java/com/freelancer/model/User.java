package com.freelancer.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	@Column(unique = true, nullable = false, updatable = false)
	private String username;

	@Column(nullable = false)
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
	private Double balance;
	private Long createAt;
	private Long updateAt;
	private Integer status;  //0.xóa 1.active 2.Deactivated

	private String thumbnail;



	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Collection<Transaction> transactions;

	@JsonBackReference(value = "freelancer")
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private UserFreelancer userFreelancers;

	@JsonBackReference(value = "business")
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private UserBusiness userBusinesses;
	
	@Transient
	List<ChatKeyUser> chatKeyUsers;

	public User(Long id, String username, String email, String password, String phone, String fullName, List<Role> roles, Double balance, Long createAt, Long updateAt, Integer status, String thumbnail) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.fullName = fullName;
		this.roles = roles;
		this.balance = balance;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.status = status;
		this.thumbnail = thumbnail;
	}
}
