package com.asolic.ReleaseManagement.services;


import com.asolic.ReleaseManagement.dto.FeatureDto;
import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.models.Feature;

import java.util.List;
import java.util.UUID;

public interface FeatureService {

    void createFeature(FeatureDto featureDto);

    Feature getFeature(String featureNameDto) throws FeatureNotFoundException;
    List<Feature> getAllFeatures() throws FeatureNotFoundException;
    Feature updateFeature(FeatureDto updatedFeatureDto, UUID featureId) throws FeatureNotFoundException;
    void deleteFeature(UUID feature) throws FeatureNotFoundException;
}
