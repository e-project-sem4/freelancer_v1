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
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_business_id;
    private Long user_freelancer_id;
    private Long proposal_id;
    private Long proposal_status_catalog_id;
    private Long massageTime;
    private String massageText;



    @ManyToOne
    @JoinColumn(name = "user_freelancer_id",referencedColumnName = "id", insertable=false, updatable=false)
    private UserFreelancer userFreelancer;

    @ManyToOne
    @JoinColumn(name = "user_business_id",referencedColumnName = "id", insertable=false, updatable=false)
    private UserBusiness userBusiness;

    @ManyToOne
    @JoinColumn(name = "proposal_id",referencedColumnName = "id", insertable=false, updatable=false)
    private Proposal proposal;

    @ManyToOne
    @JoinColumn(name = "proposal_status_catalog_id",referencedColumnName = "id", insertable=false, updatable=false)
    private ProposalStatusCatalog proposalStatusCatalog;

    @JsonBackReference
    @OneToMany(mappedBy = "message",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Attachment> attachments;

}
