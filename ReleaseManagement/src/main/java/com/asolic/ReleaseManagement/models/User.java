package com.asolic.ReleaseManagement.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    private String username;

    private String email;

    private String password;

    @Column(name="first_name")
    private String firstname;

    @Column(name="lastname")
    private String lastname;
}
