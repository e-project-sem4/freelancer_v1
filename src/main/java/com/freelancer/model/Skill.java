package com.freelancer.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skillName;
    private Long createAt;
    private Long updateAt;
    private Integer status;

    @JsonBackReference(value = "hasSkills")
    @OneToMany(mappedBy = "skill",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<HasSkill> hasSkills;


    @JsonBackReference
    @OneToMany(mappedBy = "skill",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<OtherSkill> otherSkills;

    public Skill(long id, String skillName,Integer status) {
        this.id = id;
        this.skillName = skillName;
        this.status = status;
    }
}
