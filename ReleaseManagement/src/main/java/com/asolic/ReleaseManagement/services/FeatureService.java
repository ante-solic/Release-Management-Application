package com.asolic.ReleaseManagement.services;


import com.asolic.ReleaseManagement.dto.FeatureDto;
import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.models.Client;
import com.asolic.ReleaseManagement.models.Feature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface FeatureService {

    void createFeature(FeatureDto featureDto);

    FeatureDto getFeature(UUID id) throws FeatureNotFoundException;
    Page<Feature> getAllFeatures(Pageable pageable, String filter);
    Feature updateFeature(FeatureDto updatedFeatureDto, UUID featureId) throws FeatureNotFoundException;
    void deleteFeature(UUID feature) throws FeatureNotFoundException;
    boolean isFeatureEnabled(String featureName, String accountId) throws FeatureNotFoundException;
    Set<Client> getAllFeatureClients(UUID id);
    void checkReleaseDate();
}
