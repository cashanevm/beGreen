package ua.knu.beGreen.service.mapper;

import ua.knu.beGreen.persistence.entity.ContainerEntity;
import ua.knu.beGreen.persistence.entity.MarkerEntity;
import ua.knu.beGreen.persistence.entity.UserEntity;
import ua.knu.beGreen.service.model.ContainerModel;
import ua.knu.beGreen.service.model.MarkerModel;
import ua.knu.beGreen.service.model.UserModel;

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
    @Mapping(target = "containerId", source = "container.id")
    MarkerModel toMarkerModel(MarkerEntity entity);

    /**
     * Convert MarkerModel to MarkerEntity.
     *
     * @param model - MarkerModel
     * @return MarkerEntity
     */
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "container", ignore = true)
    MarkerEntity toMarkerEntity(MarkerModel model);

    /**
     * Convert UserEntity to UserModel.
     *
     * @param entity - UserEntity
     * @return UserModel
     */
    @Mapping(target = "authorities", ignore = true)
    UserModel toUserModel(UserEntity entity);

    /**
     * Convert UserModel to UserEntity.
     *
     * @param model - UserModel
     * @return UserEntity
     */
    @Mapping(target = "containers", ignore = true)
    UserEntity toUserEntity(UserModel model);

    /**
     * Convert ContainerModel to ContainerEntity.
     *
     * @param model - ContainerModel
     * @return ContainerEntity
     */
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    ContainerEntity toContainerEntity(ContainerModel model);

    /**
     * Convert ContainerEntity to ContainerModel.
     *
     * @param entity - ContainerModel
     * @return ContainerEntity
     */
    ContainerModel toContainerModel(ContainerEntity entity);
}

// @Mapping(target = "target_field_name", source = "source_field_name")

// @Mapping(target = "target_field_name", source = "object_name.source_field_name")

// @Mapping(target = "target_field_name", ignore = true)