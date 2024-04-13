package com.asolic.ReleaseManagement.mappers;

import com.asolic.ReleaseManagement.dto.ReleaseDto;
import com.asolic.ReleaseManagement.models.Release;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ReleaseMapper {
    ReleaseDto toDto(Release release);
    Release toEntity(ReleaseDto releaseDto);
}
