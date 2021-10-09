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
@Table(name = "proposal_status_catalog")
public class ProposalStatusCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String statusName;

    @JsonBackReference(value = "proposals")
    @OneToMany(mappedBy = "proposalStatusCatalog",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Proposal> proposals;

    @JsonBackReference(value = "messages")
    @OneToMany(mappedBy = "proposalStatusCatalog",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Message> messages;
}
