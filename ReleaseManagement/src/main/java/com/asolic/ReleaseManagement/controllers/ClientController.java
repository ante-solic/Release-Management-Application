package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.dto.ClientDto;
import com.asolic.ReleaseManagement.exceptions.FeatureNotFoundException;
import com.asolic.ReleaseManagement.models.Client;
import com.asolic.ReleaseManagement.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> createClient(@RequestBody ClientDto clientDto){
        clientService.createClient(clientDto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable UUID id) throws FeatureNotFoundException {
        return clientService.getClient(id);
    }

    @GetMapping("/find/all")
    public List<Client> getAllClients() throws FeatureNotFoundException{
        return clientService.getAllClients();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Client updateClient(@RequestBody ClientDto updatedClientDto,@PathVariable UUID id) throws FeatureNotFoundException{
        return clientService.updateClient(updatedClientDto,id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteClient(@PathVariable UUID id) throws FeatureNotFoundException{
        clientService.deleteClient(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PostMapping("/assign/{clientId}/{featureId}")
    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<String> assignClient(@PathVariable UUID clientId, @PathVariable UUID featureId){
        clientService.assignClient(clientId,featureId);
        return new ResponseEntity<>("Client assigned", HttpStatus.OK);
    }

    @DeleteMapping("/unassign/{clientId}/{featureId}")
    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<String> unassignClient(@PathVariable UUID clientId, @PathVariable UUID featureId){
        clientService.unassignClient(clientId,featureId);
        return new ResponseEntity<>("Client unassigned", HttpStatus.OK);
    }
}
