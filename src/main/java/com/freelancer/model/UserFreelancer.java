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
@Table(name = "user_freelancer")
public class UserFreelancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_account_id;
    private long registrationDate;
    private String location;
    private String overview;
    private String certifications;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id",referencedColumnName = "id", insertable=false, updatable=false)
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "userFreelancer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<HasSkill> hasSkills;

    @JsonBackReference
    @OneToMany(mappedBy = "userFreelancer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Message> messages;

    @JsonBackReference
    @OneToMany(mappedBy = "userFreelancer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Proposal> proposals;

    public UserFreelancer(Long id, Long user_account_id, long registrationDate, String location, String overview, String certifications) {
        this.id = id;
        this.user_account_id = user_account_id;
        this.registrationDate = registrationDate;
        this.location = location;
        this.overview = overview;
        this.certifications = certifications;
    }
}
