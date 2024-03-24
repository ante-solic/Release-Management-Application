package com.asolic.ReleaseManagement.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="releases")
public class Release {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    private String name;

    private String description;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="release_date")
    private Date releaseDate;

    @ManyToOne
    @JoinColumn(name="project_id", referencedColumnName = "id")
    private Project project;
}
