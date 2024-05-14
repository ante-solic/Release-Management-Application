package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.ClientDto;
import com.asolic.ReleaseManagement.mappers.ClientMapper;
import com.asolic.ReleaseManagement.models.Client;
import com.asolic.ReleaseManagement.repositories.ClientRepository;
import com.asolic.ReleaseManagement.repositories.FeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final FeatureRepository featureRepository;

    public void createClient(ClientDto clientDto){
        var client = clientMapper.toEntity(clientDto);

        clientRepository.save(client);
    }

    public Client getClient(UUID id){
        var client = clientRepository.findById(id).orElseThrow();

        return client;
    }

    public List<Client> getAllClients(){
        var clients = clientRepository.findAll();

        return clients;
    }

    public Client updateClient(ClientDto updatedClientDto, UUID id){
        clientRepository.findById(id).orElseThrow();
        updatedClientDto.setId(id);

        var client = clientMapper.toEntity(updatedClientDto);

        return clientRepository.save(client);
    }

    public void deleteClient(UUID id){
        var client = clientRepository.findById(id).orElseThrow();

        clientRepository.delete(client);
    }

    public void assignClient(UUID clientId, UUID featureId){

        var client = clientRepository.findById(clientId).get();

        var feature = featureRepository.findById(featureId).get();
        Set<Client> clients = feature.getClients();
        clients.add(client);

        feature.setClients(clients);

        featureRepository.save(feature);
    }

    public void unassignClient(UUID clientId, UUID featureId){

        var client = clientRepository.findById(clientId).get();

        var feature = featureRepository.findById(featureId).get();
        Set<Client> clients = feature.getClients();
        clients.remove(client);

        feature.setClients(clients);

        featureRepository.save(feature);
    }
}
