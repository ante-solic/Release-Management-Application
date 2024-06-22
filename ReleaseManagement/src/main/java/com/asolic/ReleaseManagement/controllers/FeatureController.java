package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.dto.FeatureDto;
import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.models.Client;
import com.asolic.ReleaseManagement.models.Feature;
import com.asolic.ReleaseManagement.services.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
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
    @PreAuthorize("hasAnyRole('ROLE_DEVELOPER', 'ROLE_ADMIN')")
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
    public Page<Feature> getAllFeatures(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String filter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return featureService.getAllFeatures(pageable, filter);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DEVELOPER', 'ROLE_ADMIN')")
    public Feature updateFeature(@RequestBody FeatureDto updatedFeatureDto,@PathVariable UUID id) throws FeatureNotFoundException{
        return featureService.updateFeature(updatedFeatureDto,id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DEVELOPER', 'ROLE_ADMIN')")
    public ResponseEntity<String> deleteFeature(@PathVariable UUID id) throws FeatureNotFoundException{
        featureService.deleteFeature(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/enabled/{featureName}/{accountId}")
    public ResponseEntity<Boolean> isFeatureEnabled(@PathVariable String featureName, @PathVariable String accountId) throws FeatureNotFoundException{
        var isEnabled = featureService.isFeatureEnabled(featureName, accountId);

        return ResponseEntity.ok(isEnabled);
    }

}
