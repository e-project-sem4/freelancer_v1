package com.freelancer.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    private String user_name;
    private String password;
    @Email
    private String email;
    @Size(min = 9, max = 12, message = "phone numbers from 9 to 12 numbers")
    private String phone;
    @Size(min = 4, max = 100, message = "Minimum username length: 4 characters")
    private String full_name;
    private long balance;
}
