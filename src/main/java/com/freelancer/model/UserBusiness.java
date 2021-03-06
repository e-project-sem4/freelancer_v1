package com.freelancer.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_business")
@ToString
public class UserBusiness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_account_id;

    @Size(max = 255, message = "Max location length: 255 characters")
    @Column(nullable = false)
    private String location;

    private Long updateAt;

    @Transient
    private List<Job> listJob;

    private Integer averageGrade; //điểm trung bình



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id",referencedColumnName = "id", insertable=false, updatable=false)
    private User user;

    @JsonBackReference(value = "messages")
    @OneToMany(mappedBy = "userBusiness",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Message> messages;

    @JsonBackReference(value = "jobs")
    @OneToMany(mappedBy = "userBusiness",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Job> jobs;



    public UserBusiness(Long id, Long user_account_id, String location, Long updateAt,Integer averageGrade) {
        this.id = id;
        this.user_account_id = user_account_id;
        this.location = location;
        this.updateAt = updateAt;
        this.averageGrade = averageGrade;
    }
}
