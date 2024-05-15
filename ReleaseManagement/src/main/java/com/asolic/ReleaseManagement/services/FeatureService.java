package com.asolic.ReleaseManagement.services;


import com.asolic.ReleaseManagement.dto.FeatureDto;
import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.models.Client;
import com.asolic.ReleaseManagement.models.Feature;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface FeatureService {

    void createFeature(FeatureDto featureDto);

    FeatureDto getFeature(UUID id) throws FeatureNotFoundException;
    List<Feature> getAllFeatures() throws FeatureNotFoundException;
    Feature updateFeature(FeatureDto updatedFeatureDto, UUID featureId) throws FeatureNotFoundException;
    void deleteFeature(UUID feature) throws FeatureNotFoundException;
    boolean isFeatureEnabled(String featureName, String accountId) throws FeatureNotFoundException;
    Set<Client> getAllFeatureClients(UUID id);
    void checkReleaseDate();
}
