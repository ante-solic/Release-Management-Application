package com.asolic.ReleaseManagement.services;


import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.models.Feature;

import java.util.UUID;

public interface FeatureService {

    void createFeature(Feature feature);

    Feature getFeature(String featureName) throws FeatureNotFoundException;
    Feature updateFeature(Feature updatedFeature, UUID featureId) throws FeatureNotFoundException;

    void deleteFeature(UUID feature) throws FeatureNotFoundException;
}
