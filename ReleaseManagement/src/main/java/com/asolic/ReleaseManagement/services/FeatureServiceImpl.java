package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.FeatureDto;
import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.mappers.FeatureMapper;
import com.asolic.ReleaseManagement.models.Client;
import com.asolic.ReleaseManagement.models.Feature;
import com.asolic.ReleaseManagement.models.Release;
import com.asolic.ReleaseManagement.models.Project;
import com.asolic.ReleaseManagement.models.enums.EnableType;
import com.asolic.ReleaseManagement.repositories.ClientRepository;
import com.asolic.ReleaseManagement.repositories.FeatureRepository;
import com.asolic.ReleaseManagement.repositories.ReleaseRepository;
import com.asolic.ReleaseManagement.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.desktop.SystemSleepEvent;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FeatureServiceImpl implements FeatureService{
    private final FeatureRepository featureRepository;
    private final FeatureMapper featureMapper;
    private final ClientRepository clientRepository;
    private final ReleaseRepository releaseRepository;
    private final UserRepository userRepository;

    public void createFeature(FeatureDto featureDto){
        var feature = featureMapper.toEntity(featureDto);

        featureRepository.save(feature);
    }

    public FeatureDto getFeature(UUID id) throws FeatureNotFoundException {
        var feature = featureRepository.findById(id).orElseThrow(() -> new FeatureNotFoundException("Feature not found!"));
        var featureDto = featureMapper.toDto(feature);
        return featureDto;
    }

    public Page<Feature> getAllFeatures(Pageable pageable, String filter){

        if (filter != null && !filter.isEmpty()) {
            return featureRepository.findByNameContaining(filter, pageable);
        }

        return featureRepository.findAll(pageable);
    }

    public Page<Feature> findAllAssignedFeatures(Pageable pageable, String filter, UUID userId) {
        // Fetch user and collect project IDs
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<UUID> userProjectIds = user.getProjects().stream()
                .map(Project::getId)
                .collect(Collectors.toSet());

        // Fetch releases assigned to the user's projects
        List<Release> filteredReleases;

        if (filter != null && !filter.isEmpty()) {
            filteredReleases = releaseRepository.findByNameContaining(filter).stream()
                    .filter(release -> userProjectIds.contains(release.getProject().getId()))
                    .collect(Collectors.toList());
        } else {
            filteredReleases = releaseRepository.findAll().stream()
                    .filter(release -> userProjectIds.contains(release.getProject().getId()))
                    .collect(Collectors.toList());
        }

        Set<UUID> releaseIds = filteredReleases.stream()
                .map(Release::getId)
                .collect(Collectors.toSet());

        // Fetch features assigned to these releases
        Page<Feature> pagedFeatures;
        if (filter != null && !filter.isEmpty()) {
            pagedFeatures = featureRepository.findByNameContainingAndReleaseIdIn(filter, releaseIds, pageable);
        } else {
            pagedFeatures = featureRepository.findByReleaseIdIn(releaseIds, pageable);
        }

        // Return a new PageImpl with filtered results
        return new PageImpl<>(pagedFeatures.getContent(), pageable, pagedFeatures.getTotalElements());
    }


    public Page<Feature> findAllAssignedReleaseFeatures(Pageable pageable,String filter,UUID releaseId){
        var release = releaseRepository.findById(releaseId).get();
        if (filter != null && !filter.isEmpty()) {
            return featureRepository.findByReleaseAndNameContaining(release, filter, pageable);
        }

        return featureRepository.findByRelease(release, pageable);
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

    public boolean isFeatureEnabled(String featureName, String accountId) throws FeatureNotFoundException{
        var feature = featureRepository.findByName(featureName).orElseThrow(() -> new FeatureNotFoundException("Feature not found!"));

        if(!feature.getStatus()) {
            return false;
        }

        if(feature.getEnableType() == EnableType.ALL){
            return true;
        }

        if(feature.getEnableType() == EnableType.PER_ACCOUNT && isFeatureEnabledForAccount(feature, accountId)){
            return true;
        }

        return false;
        //        var client = clientRepository.findByAccountId(accountId);
//
//        if(feature.getEnableType() == EnableType.PER_ACCOUNT && feature.getClients().contains(client)){
//            return feature.getStatus();
//        }
//        else if (feature.getEnableType() == EnableType.ALL) {
//            return feature.getStatus();
//        }
//
//        return false;
    }

    private boolean isFeatureEnabledForAccount(Feature feature,String accountId){
        return feature.getClients()
                .stream()
                .anyMatch(client -> client.getAccountId().equals(accountId));
    }

    public Set<Client> getAllFeatureClients(UUID id){
        return featureRepository.findById(id)
                .map(Feature::getClients)
                .orElse(Collections.emptySet());
//        var feature = featureRepository.findById(id).get();
//        Set<Client> clients = feature.getClients();
//        return clients;
    }

    @Scheduled(fixedRate = 40000)
    public void checkReleaseDate(){
        var today = new Date();
        var releases = releaseRepository.findAll();

        List<Feature> featuresToUpdate = new ArrayList<>();

        for(Release release: releases){
            if (release.getReleaseDate().before(today) && release.getApproved()){
                System.out.println("Feature to update: " + release.getName());
                var features = featureRepository.findByRelease(release);
                featuresToUpdate.addAll(features);
            }
        }

        if (!featuresToUpdate.isEmpty()) {
            for (Feature feature : featuresToUpdate) {
                feature.setStatus(true);
            }
            featureRepository.saveAll(featuresToUpdate);
        }
    }


}
