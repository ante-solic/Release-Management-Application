package com.asolic.ReleaseManagement.mappers;

import com.asolic.ReleaseManagement.dto.ClientDto;
import com.asolic.ReleaseManagement.models.Client;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ClientMapper {
    ClientDto toDto(Client client);
    Client toEntity(ClientDto clientDto);
}
