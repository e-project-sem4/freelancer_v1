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
@Table(name = "has_skill")
public class HasSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_freelancer_id;
    private Long skill_id;

    @ManyToOne
    @JoinColumn(name = "user_freelancer_id",referencedColumnName = "id", insertable=false, updatable=false)
    private UserFreelancer userFreelancer;

    @ManyToOne
    @JoinColumn(name = "skill_id",referencedColumnName = "id", insertable=false, updatable=false)
    private Skill skill;

    public HasSkill(Long id, Long user_freelancer_id, Long skill_id) {
        this.id = id;
        this.user_freelancer_id = user_freelancer_id;
        this.skill_id = skill_id;
    }
}
