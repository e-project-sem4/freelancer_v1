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
    private long id;
    private long start_time;
    private long end_time;
    private long payment_amount;


    @ManyToOne
    @JoinColumn(name = "user_business_id",referencedColumnName = "id")
    private UserBusiness userBusiness;

    @ManyToOne
    @JoinColumn(name = "proposal_id",referencedColumnName = "id")
    private Proposal proposal;



}
