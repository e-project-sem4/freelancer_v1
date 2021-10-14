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
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_business")
@ToString
public class UserBusiness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_account_id;

    @Size(max = 255, message = "Max location length: 255 characters")
    @Column(nullable = false)
    private String location;

    private Long createAt;
    private Long updateAt;
    private Integer status;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id",referencedColumnName = "id", insertable=false, updatable=false)
    private User user;

    @JsonBackReference(value = "messages")
    @OneToMany(mappedBy = "userBusiness",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Message> messages;

    @JsonBackReference
    @OneToMany(mappedBy = "userBusiness",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Job> jobs;

    @JsonBackReference(value = "contracts")
    @OneToMany(mappedBy = "userBusiness",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Contract> contracts;

    public UserBusiness(Long id, Long user_account_id, String location, Long createAt, Long updateAt, Integer status) {
        this.id = id;
        this.user_account_id = user_account_id;
        this.location = location;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }
}
