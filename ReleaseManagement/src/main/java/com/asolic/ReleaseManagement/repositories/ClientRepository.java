package com.asolic.ReleaseManagement.repositories;

import com.asolic.ReleaseManagement.models.Client;
import com.asolic.ReleaseManagement.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findByAccountId(String accountId);
}
