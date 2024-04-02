package com.asolic.ReleaseManagement.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FeatureDto {
    private UUID id;
    private String name;
    private String description;
    private ReleaseDto release;
}
