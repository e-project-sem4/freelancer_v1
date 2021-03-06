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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @NotNull
    private Long user_business_id;
    @NotNull
    private Long complexity_id;
    @NotNull
    private Long expected_duration_id;
    @Size(min = 5, message = "Minimum name length: 8 characters")
    private String name;
    @Size(min = 10, message = "Minimum description length: 10 characters")
    private String description;
    @NotNull
    private Double paymentAmount;
    private Integer isPaymentStatus;
    private Long createAt;
    private Long updateAt;
    private Integer status;
    private String orderId;




    
    @OneToMany(mappedBy = "job",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Transaction> transactions;

    @ManyToOne
    @JoinColumn(name = "user_business_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserBusiness userBusiness;

    @OneToMany(mappedBy = "job", cascade = {CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.LAZY)
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
        this.isPaymentStatus = isPaymentStatus;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }
}
