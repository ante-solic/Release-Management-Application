package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.ClientDto;
import com.asolic.ReleaseManagement.models.Client;

import java.util.List;
import java.util.UUID;

public interface ClientService{

    void createClient(ClientDto clientDto);

    Client getClient(UUID id);

    List<Client> getAllClients();

    Client updateClient(ClientDto updatedClientDto, UUID id);

    void deleteClient(UUID id);

    void assignClient(UUID clientId, UUID featureId);

    void unassignClient(UUID clientId, UUID featureId);
}
