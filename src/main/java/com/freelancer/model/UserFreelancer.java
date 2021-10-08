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



    private long registration_date;
    private String location;
    private String overview;
    private String certifications;

    @ManyToOne
    @JoinColumn(name = "user_account_id",referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "userFreelancer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<HasSkill> hasSkills;

    @OneToMany(mappedBy = "userFreelancer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Message> messages;

    @OneToMany(mappedBy = "userFreelancer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Proposal> proposals;
}
