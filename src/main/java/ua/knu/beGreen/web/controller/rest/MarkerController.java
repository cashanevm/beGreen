package ua.knu.beGreen.web.controller.rest;

import ua.knu.beGreen.service.api.MarkerService;
import ua.knu.beGreen.web.dto.request.MarkerRequestDto;
import ua.knu.beGreen.web.dto.response.MarkerResponseDto;
import ua.knu.beGreen.web.mapper.WebLayerMapper;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static ua.knu.beGreen.service.constants.ApplicationConstants.REST_APPLICATION_PREFIX;

@RestController
@RequestMapping(REST_APPLICATION_PREFIX + "/map/marker")
@RequiredArgsConstructor
public class MarkerController {
    private final MarkerService markerService;

    /**
     * Get marker by id.
     *
     * @param id - marker id
     * @return MarkerResponseDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<MarkerResponseDto> getMarkerById(@PathVariable("id") String id) {
        return ResponseEntity.of(markerService.getMarkerById(id).map(WebLayerMapper.I::toMarkerResponseDto));
    }

    /**
     * Get all markers.
     *
     *
     * @return List of MarkerResponseDto
     */
    @GetMapping("/all")
    public ResponseEntity<List<MarkerResponseDto>> getAllMarkers() {
        return ResponseEntity.ok(markerService.getAllMarkers().stream()
                .map(WebLayerMapper.I::toMarkerResponseDto)
                .collect(Collectors.toList()));
    }

    /**
     * SaveMarker.
     *
     * @param requestDto - marker request dto
     * @return MarkerResponseDto
     */
    @PostMapping
    public ResponseEntity<MarkerResponseDto> saveMarker(@RequestBody @Valid MarkerRequestDto requestDto) {
        return ResponseEntity.ok(WebLayerMapper.I.toMarkerResponseDto(
                markerService.saveMarker(
                        WebLayerMapper.I.toMarkerModel(requestDto)
                )
        ));
    }
}
