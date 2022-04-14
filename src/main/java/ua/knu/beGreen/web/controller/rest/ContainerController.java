package ua.knu.beGreen.web.controller.rest;

import ua.knu.beGreen.service.api.ContainerService;
import ua.knu.beGreen.web.dto.response.ContainerResponseDto;
import ua.knu.beGreen.web.mapper.WebLayerMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static ua.knu.beGreen.service.constants.ApplicationConstants.REST_APPLICATION_PREFIX;

@RestController
@RequestMapping(REST_APPLICATION_PREFIX + "/map/container")
@RequiredArgsConstructor
public class ContainerController {
    private final ContainerService containerService;

    /**
     * Get container by id.
     *
     * @param id - container id
     * @return ContainerResponseDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContainerResponseDto> getContainerById(@PathVariable("id") String id) {
        return ResponseEntity.of(containerService.getContainerById(id).map(WebLayerMapper.I::toContainerResponseDto));
    }

    /**
     * Get container by id.
     *
     * @param id - container id
     * @return ContainerResponseDto
     */
    @DeleteMapping("/{id}")
    public void deleteContainerById(@PathVariable("id") String id) {
        containerService.delete(id);
    }
}
