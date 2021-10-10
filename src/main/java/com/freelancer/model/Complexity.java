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
@Table(name = "complexity")
public class Complexity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String complexityText;

    @JsonBackReference
    @OneToMany(mappedBy = "complexity",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Job> jobs;

    public Complexity(long id, String complexityText) {
        this.id = id;
        this.complexityText = complexityText;
    }
}
