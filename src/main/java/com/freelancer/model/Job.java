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
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private long payment_amount;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_business_id",referencedColumnName = "id")
    private UserBusiness userBusiness;

    @ManyToOne
    @JoinColumn(name = "skill_id",referencedColumnName = "id")
    private Skill skill;

    @OneToMany(mappedBy = "job",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<OtherSkill> otherSkills;

    @ManyToOne
    @JoinColumn(name = "complexity_id",referencedColumnName = "id")
    private Complexity complexity;

    @ManyToOne
    @JoinColumn(name = "expected_duration_id",referencedColumnName = "id")
    private ExpectedDuration expectedDuration;

    @OneToMany(mappedBy = "job",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Proposal> proposals;

}
