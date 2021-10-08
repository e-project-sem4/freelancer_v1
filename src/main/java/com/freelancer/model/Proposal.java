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
@Table(name = "proposal")
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long proposal_time;
    private long payment_amount;
    private int client_grade;
    private String client_comment;
    private int freelancer_grade;
    private String freelancer_comment;



    @ManyToOne
    @JoinColumn(name = "user_freelancer_id",referencedColumnName = "id")
    private UserFreelancer userFreelancer;

    @ManyToOne
    @JoinColumn(name = "job_id",referencedColumnName = "id")
    private Job job;

    @OneToMany(mappedBy = "proposal",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Contract> contracts;

    @OneToMany(mappedBy = "proposal",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Message> messages;

    @ManyToOne
    @JoinColumn(name = "proposal_status_catalog_id",referencedColumnName = "id")
    private ProposalStatusCatalog proposalStatusCatalog;


}
