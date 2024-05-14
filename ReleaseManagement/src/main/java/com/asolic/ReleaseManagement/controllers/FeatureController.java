package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.dto.FeatureDto;
import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.models.Client;
import com.asolic.ReleaseManagement.models.Feature;
import com.asolic.ReleaseManagement.services.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/feature")
public class FeatureController {
    @Autowired
    private FeatureService featureService;

    @PostMapping("/create")
    public ResponseEntity<String> createFeature(@RequestBody FeatureDto featureDto){
        featureService.createFeature(featureDto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public FeatureDto getFeature(@PathVariable UUID id) throws FeatureNotFoundException {
        return featureService.getFeature(id);
    }

    @GetMapping("/clients/{id}")
    public Set<Client> getFeatureClients(@PathVariable UUID id){
        return featureService.getAllFeatureClients(id);
    }

    @GetMapping("/find/all")
    public List<Feature> getAllFeatures() throws FeatureNotFoundException{
        return featureService.getAllFeatures();
    }

    @PutMapping("/update/{id}")
    public Feature updateFeature(@RequestBody FeatureDto updatedFeatureDto,@PathVariable UUID id) throws FeatureNotFoundException{
        return featureService.updateFeature(updatedFeatureDto,id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFeature(@PathVariable UUID id) throws FeatureNotFoundException{
        featureService.deleteFeature(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/is-enabled/{featureName}")
    public ResponseEntity<Boolean> isFeatureEnabled(@PathVariable String featureName) throws FeatureNotFoundException{
        var isEnabled = featureService.isFeatureEnabled(featureName);

        return ResponseEntity.ok(isEnabled);
    }


}
