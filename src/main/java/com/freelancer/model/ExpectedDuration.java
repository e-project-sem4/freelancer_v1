package com.freelancer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "expected_duration")
public class ExpectedDuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String durationText;
    private Long createAt;
    private Long updateAt;
    private Integer status;

    @JsonBackReference
    @OneToMany(mappedBy = "expectedDuration",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Job> jobs;

    public ExpectedDuration(long id, String durationText,Integer status) {
        this.id = id;
        this.durationText = durationText;
        this.status = status;
    }
}
