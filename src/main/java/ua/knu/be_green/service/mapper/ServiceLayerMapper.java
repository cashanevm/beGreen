package ua.knu.be_green.service.mapper;

import ua.knu.be_green.persistence.entity.MarkerEntity;
import ua.knu.be_green.service.model.MarkerModel;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, builder = @Builder(disableBuilder = true))
public interface ServiceLayerMapper {
    /**
     * I.
     */
    ServiceLayerMapper I = Mappers.getMapper(ServiceLayerMapper.class);

    /**
     * Convert MarkerEntity to MarkerModel.
     *
     * @param entity - MarkerEntity
     * @return MarkerModel
     */
    MarkerModel toMarkerModel(MarkerEntity entity);

    /**
     * Convert MarkerModel to MarkerEntity.
     *
     * @param model - MarkerModel
     * @return MarkerEntity
     */
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    MarkerEntity toMarkerEntity(MarkerModel model);
}

// @Mapping(target = "target_field_name", source = "source_field_name")

// @Mapping(target = "target_field_name", source = "object_name.source_field_name")

// @Mapping(target = "target_field_name", ignore = true)