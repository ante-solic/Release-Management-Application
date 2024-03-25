package com.asolic.ReleaseManagement.repositories;

import com.asolic.ReleaseManagement.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findById(UUID id);

    Optional<Project> findByName(String name);
}
