package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.models.Feature;
import com.asolic.ReleaseManagement.services.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/feature")
public class FeatureController {
    @Autowired
    private FeatureService featureService;

    @PostMapping("/create")
    public String createFeature(@RequestBody Feature feature){
        featureService.createFeature(feature);
        return "success";
    }

    @GetMapping("/find")
    public Feature getFeature(@RequestBody Feature feature) throws FeatureNotFoundException {
        return featureService.getFeature(feature.getName());
    }

    @PostMapping("/update/{id}")
    public Feature updateFeature(@RequestBody Feature updatedFeature,@PathVariable UUID id) throws FeatureNotFoundException{
        return featureService.updateFeature(updatedFeature,id);
    }

    @PostMapping("/delete/{id}")
    public String deleteFeature(@PathVariable UUID id) throws FeatureNotFoundException{
        featureService.deleteFeature(id);
        return "deleted";
    }
}
