package com.asolic.ReleaseManagement.mappers;

import com.asolic.ReleaseManagement.dto.ReleaseDto;
import com.asolic.ReleaseManagement.models.Release;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReleaseMapper {
    ReleaseDto toDto(Release release);
    Release toEntity(ReleaseDto releaseDto);
}
