package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.FeatureDto;
import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.mappers.FeatureMapper;
import com.asolic.ReleaseManagement.models.Feature;
import com.asolic.ReleaseManagement.repositories.FeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FeatureServiceImpl implements FeatureService{
    private final FeatureRepository featureRepository;
    private final FeatureMapper featureMapper;

    public void createFeature(FeatureDto featureDto){
        var feature = featureMapper.toEntity(featureDto);

        featureRepository.save(feature);
    }

    public Feature getFeature(UUID id) throws FeatureNotFoundException {
        var feature = featureRepository.findById(id).orElseThrow(() -> new FeatureNotFoundException("Feature not found!"));

        return feature;
    }

    public List<Feature> getAllFeatures() throws FeatureNotFoundException{
        var features = featureRepository.findAll();

        if(features.isEmpty()){
            throw new FeatureNotFoundException("No features found!");
        }

        return features;
    }

    public Feature updateFeature(FeatureDto updatedFeatureDto, UUID featureId) throws FeatureNotFoundException{
        featureRepository.findById(featureId).orElseThrow(() -> new FeatureNotFoundException("Feature not found!"));;
        updatedFeatureDto.setId(featureId);

        var feature = featureMapper.toEntity(updatedFeatureDto);

        featureRepository.save(feature);

        return feature;
    }

    public void deleteFeature(UUID featureId) throws FeatureNotFoundException{
        var feature = featureRepository.findById(featureId).orElseThrow(() -> new FeatureNotFoundException("Feature not found!"));

        featureRepository.delete(feature);
    }
}
