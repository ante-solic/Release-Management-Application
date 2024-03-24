package com.asolic.ReleaseManagement.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="user_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    private String name;

    private String description;
}
