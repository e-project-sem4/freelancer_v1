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
    private Long id;
    private Long job_id;
    private Long skill_id;

    @ManyToOne
    @JoinColumn(name = "skill_id",referencedColumnName = "id", insertable=false, updatable=false)
    private Skill skill;

    @ManyToOne
    @JoinColumn(name = "job_id",referencedColumnName = "id", insertable=false, updatable=false)
    private Job job;

    public OtherSkill(Long id, Long job_id, Long skill_id) {
        this.id = id;
        this.job_id = job_id;
        this.skill_id = skill_id;
    }
}
