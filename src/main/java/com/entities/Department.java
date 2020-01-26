package com.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = "lectors")
@Entity
public class Department implements Serializable {

    private static final long serialVersionUID = -1069797329911616484L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "head_lector_id", referencedColumnName = "id")
    private Lector head;

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany
    @JoinTable(name = "department_lector",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "lector_id"))
    private Set<Lector> lectors = new HashSet<>();

    public Department(String name, Lector head, Collection<Lector> lectors) {
        lectors.forEach(this::addLector);
        addLector(head);
        this.name = name;
        this.head = head;
    }

    public void addLector(Lector lector) {
        lector.getDepartments().add(this);
        lectors.add(lector);
    }

    public void removeLector(Lector lector) {
        lector.getDepartments().remove(this);
        lectors.remove(lector);
    }

    public void setHead(Lector head) {
        this.head = head;
        addLector(head);
    }
}
