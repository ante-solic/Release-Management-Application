package com.asolic.ReleaseManagement.repositories;

import com.asolic.ReleaseManagement.models.Feature;
import com.asolic.ReleaseManagement.models.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    List<Feature> findByRelease(Release release);
}
