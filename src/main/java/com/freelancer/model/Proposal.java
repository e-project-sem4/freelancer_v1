package com.freelancer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Long id;
    private Long proposalTime;
    private Long paymentAmount;
    private int clientGrade;
    private String clientComment;
    private int freelancerGrade;
    private String freelancerComment;
    private Long user_freelancer_id;
    private Long job_id;
    private Long proposal_status_catalog_id;

    @ManyToOne
    @JoinColumn(name = "user_freelancer_id",referencedColumnName = "id", insertable=false, updatable=false)
    private UserFreelancer userFreelancer;

    @ManyToOne
    @JoinColumn(name = "job_id",referencedColumnName = "id", insertable=false, updatable=false)
    private Job job;

    @JsonBackReference(value = "contracts")
    @OneToMany(mappedBy = "proposal",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Contract> contracts;

    @JsonBackReference(value = "messages")
    @OneToMany(mappedBy = "proposal",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Message> messages;

    @ManyToOne
    @JoinColumn(name = "proposal_status_catalog_id",referencedColumnName = "id", insertable=false, updatable=false)
    private ProposalStatusCatalog proposalStatusCatalog;


}
