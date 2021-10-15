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
    private Long user_freelancer_id;
    private Long job_id;
    private Double paymentAmount;
    private String description;
    private Integer clientGrade;
    private String clientComment;
    private Integer freelancerGrade;
    private String freelancerComment;
    private Long createAt;
    private Long updateAt;
    private Long proposal_status_catalog_id;

    @Transient
    private String jobName;

    @JsonBackReference(value = "freelancer")
    @ManyToOne
    @JoinColumn(name = "user_freelancer_id",referencedColumnName = "id", insertable=false, updatable=false)
    private UserFreelancer userFreelancer;

    @JsonBackReference(value = "job")
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

    public Proposal(Long id, Double paymentAmount, String description, Integer clientGrade, String clientComment, Integer freelancerGrade, String freelancerComment, Long user_freelancer_id, Long job_id, Long proposal_status_catalog_id) {
        this.id = id;
        this.paymentAmount = paymentAmount;
        this.description = description;
        this.clientGrade = clientGrade;
        this.clientComment = clientComment;
        this.freelancerGrade = freelancerGrade;
        this.freelancerComment = freelancerComment;
        this.user_freelancer_id = user_freelancer_id;
        this.job_id = job_id;
        this.proposal_status_catalog_id = proposal_status_catalog_id;
    }

    public Proposal(Long id,Long createAt, Double paymentAmount, String description, Long user_freelancer_id, Long job_id, Long proposal_status_catalog_id) {
        this.id = id;
        this.createAt = createAt;
        this.paymentAmount = paymentAmount;
        this.description = description;
        this.user_freelancer_id = user_freelancer_id;
        this.job_id = job_id;
        this.proposal_status_catalog_id = proposal_status_catalog_id;

    }
}
