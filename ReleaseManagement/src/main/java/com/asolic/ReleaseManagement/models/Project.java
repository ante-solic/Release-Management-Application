package com.asolic.ReleaseManagement.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    private String name;
}
