package com.freelancer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

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
