package com.freelancer.model.proposalAndContract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "proposal")
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long job_id;
    private long user_freelancer_id;
    private long proposal_time;
    private long payment_amount;
    private long proposal_status_catalog_id;
    private int client_grade;
    private String client_comment;
    private int freelancer_grade;
    private String freelancer_comment;
}
