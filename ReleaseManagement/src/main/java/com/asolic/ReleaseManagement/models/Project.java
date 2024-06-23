package com.asolic.ReleaseManagement.models;

import com.asolic.ReleaseManagement.repositories.ProjectRepository;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    private String name;

    public Project() {
    }
    public Project(String name){
        this.name = name;
    }
}
