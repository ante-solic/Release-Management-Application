package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.FeatureDto;
import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.mappers.FeatureMapper;
import com.asolic.ReleaseManagement.models.Client;
import com.asolic.ReleaseManagement.models.Feature;
import com.asolic.ReleaseManagement.models.enums.EnableType;
import com.asolic.ReleaseManagement.repositories.FeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.desktop.SystemSleepEvent;
import java.util.List;
import java.util.Set;
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

    public FeatureDto getFeature(UUID id) throws FeatureNotFoundException {
        var feature = featureRepository.findById(id).orElseThrow(() -> new FeatureNotFoundException("Feature not found!"));
        var featureDto = featureMapper.toDto(feature);
        return featureDto;
    }

    public List<Feature> getAllFeatures() throws FeatureNotFoundException{
        var features = featureRepository.findAll();

        if(features.isEmpty()){
            throw new FeatureNotFoundException("No features found!");
        }

        return features;
    }

    public Feature updateFeature(FeatureDto updatedFeatureDto, UUID featureId) throws FeatureNotFoundException{
        var feature = featureRepository.findById(featureId).orElseThrow(() -> new FeatureNotFoundException("Feature not found!"));;

        feature.setName(updatedFeatureDto.getName());
        feature.setDescription(updatedFeatureDto.getDescription());
        feature.setStatus(updatedFeatureDto.getStatus());

        var enableTypeStr = updatedFeatureDto.getEnableType();

        var enableType = EnableType.valueOf(enableTypeStr);

        feature.setEnableType(enableType);

        featureRepository.save(feature);

        return feature;
    }

    public void deleteFeature(UUID featureId) throws FeatureNotFoundException{
        var feature = featureRepository.findById(featureId).orElseThrow(() -> new FeatureNotFoundException("Feature not found!"));

        featureRepository.delete(feature);
    }

    public boolean isFeatureEnabled(String featureName) throws FeatureNotFoundException{
        var feature = featureRepository.findByName(featureName).orElseThrow(() -> new FeatureNotFoundException("Feature not found!"));

        return feature.getStatus();
    }

    public Set<Client> getAllFeatureClients(UUID id){
        var feature = featureRepository.findById(id).get();
        Set<Client> clients = feature.getClients();
        return clients;
    }
}
