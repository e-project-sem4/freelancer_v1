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
    private Long id;
    private Long user_business_id;
    private Long complexity_id;
    private Long expected_duration_id;
    private Long skill_id;
    private String name;
    private String description;
    private long payment_amount;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_business_id",referencedColumnName = "id", insertable=false, updatable=false)
    private UserBusiness userBusiness;

    @ManyToOne
    @JoinColumn(name = "skill_id",referencedColumnName = "id", insertable=false, updatable=false)
    private Skill skill;

    @OneToMany(mappedBy = "job",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<OtherSkill> otherSkills;

    @ManyToOne
    @JoinColumn(name = "complexity_id",referencedColumnName = "id", insertable=false, updatable=false)
    private Complexity complexity;

    @ManyToOne
    @JoinColumn(name = "expected_duration_id",referencedColumnName = "id", insertable=false, updatable=false)
    private ExpectedDuration expectedDuration;

    @OneToMany(mappedBy = "job",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Proposal> proposals;

    public Job(Long id, Long user_business_id, Long complexity_id, Long expected_duration_id, Long skill_id, String name, String description, long payment_amount) {
        this.id = id;
        this.user_business_id = user_business_id;
        this.complexity_id = complexity_id;
        this.expected_duration_id = expected_duration_id;
        this.skill_id = skill_id;
        this.name = name;
        this.description = description;
        this.payment_amount = payment_amount;
    }
}
