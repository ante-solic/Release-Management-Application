package com.asolic.ReleaseManagement.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ReleaseDto {
    private UUID id;
    private String name;
    private String description;
    private Date createDate;
    private Date releaseDate;
    private ProjectDto project;
    private Boolean approved;
}
