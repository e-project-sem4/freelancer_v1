package com.freelancer.model;

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
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long startTime;
    private Long endTime;
    private Long paymentAmount;
    private Long user_business_id;
    private Long proposal_id;


    @ManyToOne
    @JoinColumn(name = "user_business_id",referencedColumnName = "id", insertable=false, updatable=false)
    private UserBusiness userBusiness;

    @ManyToOne
    @JoinColumn(name = "proposal_id",referencedColumnName = "id", insertable=false, updatable=false)
    private Proposal proposal;



}
