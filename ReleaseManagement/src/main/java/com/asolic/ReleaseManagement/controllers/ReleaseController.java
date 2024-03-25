package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.exceptions.ReleaseNotFoundException;
import com.asolic.ReleaseManagement.models.Release;
import com.asolic.ReleaseManagement.services.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/release")
public class ReleaseController {
    @Autowired
    private ReleaseService releaseService;

    @PostMapping("/create")
    public String createRelease(@RequestBody Release release){
        releaseService.createRelease(release);
        return "success";
    }

    @GetMapping("/find")
    public Release getRelease(@RequestBody Release release) throws ReleaseNotFoundException {
        return releaseService.findReleaseById(release.getId());
    }

    @PostMapping("/update/{releaseId}")
    public Release updateRelease(@RequestBody Release updatedRelease, @PathVariable UUID releaseId){
        return releaseService.updateRelease(updatedRelease, releaseId);
    }

    @PostMapping("/delete/{releaseId}")
    public String deleteRelease(@PathVariable UUID releaseId) throws ReleaseNotFoundException{
        releaseService.deleteReleaseById(releaseId);
        return "deleted";
    }
}
