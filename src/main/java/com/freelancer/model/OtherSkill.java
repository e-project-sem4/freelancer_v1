package com.freelancer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "other_skill")
public class OtherSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long job_id;

    @ManyToOne
    @JoinColumn(name = "skill_id",referencedColumnName = "id")
    private Skill skill;

    @ManyToOne
    @JoinColumn(name = "job_id",referencedColumnName = "id", insertable=false, updatable=false)
    private Job job;
}
