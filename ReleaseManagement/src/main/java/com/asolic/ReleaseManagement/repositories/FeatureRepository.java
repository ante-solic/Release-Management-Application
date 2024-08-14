package com.asolic.ReleaseManagement.repositories;

import com.asolic.ReleaseManagement.models.Feature;
import com.asolic.ReleaseManagement.models.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, UUID> {
    Optional<Feature> findByName(String name);
    List<Feature> findByRelease(Release release);
    Page<Feature> findByRelease(Release release, Pageable pageable);
    Page<Feature> findByNameContaining(String name, Pageable pageable);
    Page<Feature> findByReleaseAndNameContaining(Release release, String name, Pageable pageable);
    Page<Feature> findByNameContainingAndReleaseIdIn(String name, Set<UUID> releaseIds, Pageable pageable);
    Page<Feature> findByReleaseIdIn(Set<UUID> releaseIds, Pageable pageable);
}
