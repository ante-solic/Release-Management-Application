package com.asolic.ReleaseManagement.repositories;

import com.asolic.ReleaseManagement.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Optional<Project> findByName(String name);
    Page<Project> findByNameContaining(String name, Pageable pageable);
}
