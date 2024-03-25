package com.asolic.ReleaseManagement.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name="features")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name="release_id", referencedColumnName = "id")
    private Release release;
}
