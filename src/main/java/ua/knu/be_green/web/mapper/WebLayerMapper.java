package ua.knu.be_green.web.mapper;

import ua.knu.be_green.persistence.entity.MarkerEntity;
import ua.knu.be_green.service.model.MarkerModel;
import ua.knu.be_green.web.dto.request.MarkerRequestDto;
import ua.knu.be_green.web.dto.response.MarkerResponseDto;

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
     MarkerResponseDto toMarkerResponseDto(MarkerModel model);

    /**
     * Convert MarkerRequestDto to MarkerModel.
     *
     * @param requestDto - MarkerRequestDto
     * @return MarkerModel
     */
    @Mapping(target = "id", ignore = true)
    MarkerModel toMarkerModel(MarkerRequestDto requestDto);
}
