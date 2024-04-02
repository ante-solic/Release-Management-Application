package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.dto.ReleaseDto;
import com.asolic.ReleaseManagement.exceptions.ReleaseNotFoundException;
import com.asolic.ReleaseManagement.models.Release;
import com.asolic.ReleaseManagement.services.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/release")
public class ReleaseController {
    @Autowired
    private ReleaseService releaseService;

    @PostMapping("/create")
    public String createRelease(@RequestBody ReleaseDto releaseDto){
        releaseService.createRelease(releaseDto);
        return "success";
    }

    @GetMapping("/find")
    public Release getRelease(@RequestBody ReleaseDto releaseDto) throws ReleaseNotFoundException {
        return releaseService.findReleaseById(releaseDto.getId());
    }

    @GetMapping("/find/all")
    public List<Release> getAllReleases() throws ReleaseNotFoundException{
        return releaseService.findAllReleases();
    }

    @PostMapping("/update/{releaseId}")
    public Release updateRelease(@RequestBody ReleaseDto updatedReleaseDto, @PathVariable UUID releaseId) throws ReleaseNotFoundException{
        return releaseService.updateRelease(updatedReleaseDto, releaseId);
    }

    @PostMapping("/delete/{releaseId}")
    public String deleteRelease(@PathVariable UUID releaseId) throws ReleaseNotFoundException{
        releaseService.deleteReleaseById(releaseId);
        return "deleted";
    }
}
