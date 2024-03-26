package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.models.Feature;
import com.asolic.ReleaseManagement.repositories.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FeatureServiceImpl implements FeatureService{
    @Autowired
    private FeatureRepository featureRepository;

    public void createFeature(Feature feature){
        featureRepository.save(feature);
    }

    public Feature getFeature(String featureName) throws FeatureNotFoundException {
        var feature = featureRepository.findByName(featureName).orElseThrow(() -> new FeatureNotFoundException("Feature not found!"));

        return feature;
    }

    public Feature updateFeature(Feature updatedFeature, UUID featureId) throws FeatureNotFoundException{
        var feature = featureRepository.findById(featureId).orElseThrow(() -> new FeatureNotFoundException("Feature not found!"));;

        feature.setName(updatedFeature.getName());
        feature.setDescription(updatedFeature.getDescription());

        featureRepository.save(feature);

        return feature;
    }

    public void deleteFeature(UUID featureId) throws FeatureNotFoundException{
        var feature = featureRepository.findById(featureId).orElseThrow(() -> new FeatureNotFoundException("Feature not found!"));

        featureRepository.delete(feature);
    }
}
