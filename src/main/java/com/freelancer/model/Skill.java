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
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String skill_name;

    @OneToMany(mappedBy = "skill",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<HasSkill> hasSkills;

    @OneToMany(mappedBy = "skill",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Job> jobs;

    @OneToMany(mappedBy = "skill",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<OtherSkill> otherSkills;
}
