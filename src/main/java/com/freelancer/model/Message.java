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
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long user_business_id;
    private long massage_time;
    private String massage_text;



    @ManyToOne
    @JoinColumn(name = "user_freelancer_id",referencedColumnName = "id")
    private UserFreelancer userFreelancer;

    @ManyToOne
    @JoinColumn(name = "user_business_id",referencedColumnName = "id", insertable=false, updatable=false)
    private UserBusiness userBusiness;

    @ManyToOne
    @JoinColumn(name = "proposal_id",referencedColumnName = "id")
    private Proposal proposal;

    @ManyToOne
    @JoinColumn(name = "proposal_status_catalog_id",referencedColumnName = "id")
    private ProposalStatusCatalog proposalStatusCatalog;

    @OneToMany(mappedBy = "message",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Attachment> attachments;

}
