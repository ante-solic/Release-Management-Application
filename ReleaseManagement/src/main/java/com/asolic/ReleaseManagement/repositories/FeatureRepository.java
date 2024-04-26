package com.asolic.ReleaseManagement.repositories;

import com.asolic.ReleaseManagement.models.Feature;
import com.asolic.ReleaseManagement.models.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, UUID> {

    Optional<Feature> findByName(String name);
    List<Feature> findByRelease(Release release);
}
