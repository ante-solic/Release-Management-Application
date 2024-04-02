package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.dto.FeatureDto;
import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.models.Feature;
import com.asolic.ReleaseManagement.services.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/feature")
public class FeatureController {
    @Autowired
    private FeatureService featureService;

    @PostMapping("/create")
    public String createFeature(@RequestBody FeatureDto featureDto){
        featureService.createFeature(featureDto);
        return "success";
    }

    @GetMapping("/find")
    public Feature getFeature(@RequestBody FeatureDto featureDto) throws FeatureNotFoundException {
        return featureService.getFeature(featureDto.getName());
    }

    @GetMapping("/find/all")
    public List<Feature> getAllFeatures() throws FeatureNotFoundException{
        return featureService.getAllFeatures();
    }

    @PostMapping("/update/{id}")
    public Feature updateFeature(@RequestBody FeatureDto updatedFeatureDto,@PathVariable UUID id) throws FeatureNotFoundException{
        return featureService.updateFeature(updatedFeatureDto,id);
    }

    @PostMapping("/delete/{id}")
    public String deleteFeature(@PathVariable UUID id) throws FeatureNotFoundException{
        featureService.deleteFeature(id);
        return "deleted";
    }
}
