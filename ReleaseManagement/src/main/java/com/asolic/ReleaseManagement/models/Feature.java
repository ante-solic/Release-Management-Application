package com.asolic.ReleaseManagement.models;

import com.asolic.ReleaseManagement.models.enums.EnableType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
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

    private Boolean status;

    @Column(name = "enable_type", columnDefinition = "enum_defination")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private EnableType enableType;

    @ManyToOne
    @JoinColumn(name="release_id", referencedColumnName = "id")
    private Release release;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "feature_clients",
            joinColumns = @JoinColumn(name = "feature_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private Set<Client> clients = new HashSet<>();
}
