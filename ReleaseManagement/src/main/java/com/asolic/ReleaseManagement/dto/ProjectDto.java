package com.asolic.ReleaseManagement.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProjectDto {
    private UUID id;
    private String name;
    private String username;
}
