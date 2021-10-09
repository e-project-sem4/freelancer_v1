package com.freelancer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_business")
public class UserBusiness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_account_id;
    private long registration_date;
    @Size(max = 255, message = "Max location length: 255 characters")
    @Column(nullable = false)
    private String location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id",referencedColumnName = "id", insertable=false, updatable=false)
    private User user;

    @OneToMany(mappedBy = "userBusiness",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Message> messages;

    @OneToMany(mappedBy = "userBusiness",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Job> jobs;

    @OneToMany(mappedBy = "userBusiness",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Contract> contracts;

    public UserBusiness(Long id, Long user_account_id, long registration_date, String location) {
        this.id = id;
        this.user_account_id = user_account_id;
        this.registration_date = registration_date;
        this.location = location;
    }
}
