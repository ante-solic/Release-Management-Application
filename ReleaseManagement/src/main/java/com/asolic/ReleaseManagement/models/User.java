package com.asolic.ReleaseManagement.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
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

    @Column(name="last_name")
    private String lastname;
}
