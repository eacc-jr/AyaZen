package com.ayazen.AyaZen.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "task")
public class AyazenTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String status;


     @ManyToOne
     @JoinColumn(name = "project_id")
     @JsonIgnoreProperties("tasks")
     private AyazenProject project;

    public AyazenTask(Long id, String name, String description, String status, Instant creationTimestamp, Instant updateTimestamp, AyazenProject project) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.project = project;
    }

    public AyazenTask() {
    }

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

    public AyazenProject getProject() {
        return project;
    }

   public void setProject(AyazenProject project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AyazenTask that = (AyazenTask) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
