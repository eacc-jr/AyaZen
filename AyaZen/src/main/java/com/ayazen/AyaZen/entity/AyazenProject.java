package com.ayazen.AyaZen.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "project")
public class AyazenProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private String status;


   @ManyToOne
    @JoinColumn(name = "customer_id")
    private AyazenCustomer customer;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<AyazenTask> tasks = new ArrayList<AyazenTask>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AyazenCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(AyazenCustomer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AyazenProject that = (AyazenProject) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public List<AyazenTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<AyazenTask> tasks) {
        this.tasks = tasks;
    }

}

