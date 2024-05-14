package com.asolic.ReleaseManagement.mappers;

import com.asolic.ReleaseManagement.dto.FeatureDto;
import com.asolic.ReleaseManagement.models.Feature;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface FeatureMapper {
    FeatureDto toDto(Feature feature);

    Feature toEntity(FeatureDto featureDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "feature.name", source = "featureDto.name")
    @Mapping(target = "feature.description", source = "featureDto.description")
    @Mapping(target = "feature.release", source = "featureDto.release")
    @Mapping(target = "feature.status", source = "featureDto.status")
    @Mapping(target = "feature.enableType", source = "featureDto.enableType")
    Feature updateToEntity(FeatureDto featureDto, @MappingTarget Feature feature);
}
