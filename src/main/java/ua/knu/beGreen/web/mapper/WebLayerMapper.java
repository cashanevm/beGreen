package ua.knu.beGreen.web.mapper;

import ua.knu.beGreen.service.model.ContainerModel;
import ua.knu.beGreen.service.model.MarkerModel;
import ua.knu.beGreen.service.model.UserModel;
import ua.knu.beGreen.web.dto.request.ContainerRequestDto;
import ua.knu.beGreen.web.dto.request.MarkerRequestDto;
import ua.knu.beGreen.web.dto.request.UserRequestDto;
import ua.knu.beGreen.web.dto.response.ContainerResponseDto;
import ua.knu.beGreen.web.dto.response.MarkerResponseDto;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, builder = @Builder(disableBuilder = true))
public interface WebLayerMapper {
    /**
     * I.
     */
    WebLayerMapper I = Mappers.getMapper(WebLayerMapper.class);

    /**
     * Convert MarkerModel to MarkerResponseDto.
     *
     * @param model - MarkerModel
     * @return MarkerResponseDto
     */
    //@Mapping(target = "containerId", source = "container.id")
     MarkerResponseDto toMarkerResponseDto(MarkerModel model);

    /**
     * Convert MarkerRequestDto to MarkerModel.
     *
     * @param requestDto - MarkerRequestDto
     * @return MarkerModel
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "containerId", ignore = true)
    MarkerModel toMarkerModel(MarkerRequestDto requestDto);

    /**
     * Convert UserRequestDto to UserModel.
     *
     * @param requestDto - UserRequestDto
     * @return UserModel
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activationCode", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    UserModel toUserModel(UserRequestDto requestDto);

    /**
     * Convert ContainerRequestDto to ContainerModel.
     *
     * @param requestDto - ContainerRequestDto
     * @return ContainerModel
     */
    ContainerModel toContainerModel(ContainerRequestDto requestDto);

    /**
     * Convert ContainerModel to ContainerResponseDto.
     *
     * @param model - ContainerModel
     * @return ContainerResponseDto
     */
    @Mapping(target = "ownerName", source = "user.username")
    ContainerResponseDto toContainerResponseDto(ContainerModel model);

    ContainerRequestDto toContainerRequestDto(ContainerModel model);
}
