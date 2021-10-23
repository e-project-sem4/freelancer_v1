package com.freelancer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // khóa chính
    private Double price;
    private Long createAt;
    private String content;
    private String orderID;
    private Integer typeTransaction;
    private Long job_id;  // khóa ngoại
    private Long user_account_id; // khóa ngoại

    @JsonBackReference(value = "job")
    @ManyToOne
    @JoinColumn(name = "job_id",referencedColumnName = "id", insertable=false, updatable=false)
    private Job job;

    @JsonBackReference(value = "user")
    @ManyToOne
    @JoinColumn(name = "user_account_id ",referencedColumnName = "id", insertable=false, updatable=false)
    private User user;

}
