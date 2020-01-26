package com.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "departments")
@NoArgsConstructor
@Entity
@Table(name = "Lector")
public class Lector implements Serializable {

    private static final long serialVersionUID = 3644595309230562728L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "degree")
    private Degree degree;

    @Column(name = "salary")
    private double salary;

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "lectors")
    private Set<Department> departments = new HashSet<>();

    public Lector(String name, Degree degree, double salary) {
        this.name = name;
        this.degree = degree;
        this.salary = salary;
    }
}
