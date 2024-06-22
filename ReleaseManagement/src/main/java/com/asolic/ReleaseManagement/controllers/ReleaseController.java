package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.dto.ReleaseDto;
import com.asolic.ReleaseManagement.exceptions.ReleaseNotFoundException;
import com.asolic.ReleaseManagement.models.Release;
import com.asolic.ReleaseManagement.services.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> createRelease(@RequestBody ReleaseDto releaseDto){
        releaseService.createRelease(releaseDto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Release getRelease(@PathVariable UUID id) throws ReleaseNotFoundException {
        return releaseService.findReleaseById(id);
    }

    @GetMapping("/find/all")
    public Page<Release> getAllReleases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String filter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return releaseService.findAllReleases(pageable, filter);
    }

    @PutMapping("/update/{releaseId}")
    public Release updateRelease(@RequestBody ReleaseDto updatedReleaseDto, @PathVariable UUID releaseId) throws ReleaseNotFoundException{
        return releaseService.updateRelease(updatedReleaseDto, releaseId);
    }

    @DeleteMapping("/delete/{releaseId}")
    public ResponseEntity<String> deleteRelease(@PathVariable UUID releaseId) throws ReleaseNotFoundException{
        releaseService.deleteReleaseById(releaseId);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
