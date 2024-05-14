package com.asolic.ReleaseManagement.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientDto {
    private UUID id;
    private String accountId;
}
