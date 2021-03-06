package com.freelancer.model;

import javax.persistence.*;

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
@Table(name = "contract")
@ToString
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long startTime;
    private Long endTime;
    private Double paymentAmount;
    private Long user_business_id;

    @Column(unique = true, nullable = false, updatable = false)
    private Long proposal_id;

    private Integer status;
    
    @Transient
    private ChatKeyUser chatKeyUser;




    @JsonBackReference(value = "contracts")
    @ManyToOne
    @JoinColumn(name = "proposal_id",referencedColumnName = "id", insertable=false, updatable=false)
    private Proposal proposal;



}
