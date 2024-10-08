package com.asolic.ReleaseManagement.repositories;

import com.asolic.ReleaseManagement.models.Project;
import com.asolic.ReleaseManagement.models.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, UUID> {
    List<Release> findByProject(Project project);

    Page<Release> findByProjectAndNameContaining(Project project, String name, Pageable pageable);

    Page<Release> findByProject(Project project, Pageable pageable);

    Page<Release> findByNameContaining(String name, Pageable pageable);

    List<Release> findByNameContaining(String name);
}
