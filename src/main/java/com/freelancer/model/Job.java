package com.freelancer.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_business_id;
    private Long complexity_id;
    private Long expected_duration_id;
    private String name;
    private String description;
    private Double paymentAmount;
    private Long createAt;
    private Long updateAt;
    private Integer status;
    private Integer isPaymentStatus;


    @ManyToOne
    @JoinColumn(name = "user_business_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserBusiness userBusiness;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<OtherSkill> otherSkills;

    @ManyToOne
    @JoinColumn(name = "complexity_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Complexity complexity;

    @ManyToOne
    @JoinColumn(name = "expected_duration_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ExpectedDuration expectedDuration;


    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Proposal> proposals;

    public Job(Long id, Long user_business_id, Long complexity_id, Long expected_duration_id, String name, String description, Double paymentAmount, Integer isPaymentStatus, Long createAt, Long updateAt, Integer status) {
        this.id = id;
        this.user_business_id = user_business_id;
        this.complexity_id = complexity_id;
        this.expected_duration_id = expected_duration_id;
        this.name = name;
        this.description = description;
        this.paymentAmount = paymentAmount;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }
}
