package ua.knu.beGreen.service.api.impl;

import ua.knu.beGreen.persistence.repository.MarkerRepository;
import ua.knu.beGreen.service.api.ContainerService;
import ua.knu.beGreen.service.api.MarkerService;
import ua.knu.beGreen.service.mapper.ServiceLayerMapper;
import ua.knu.beGreen.service.model.ContainerModel;
import ua.knu.beGreen.service.model.MarkerModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarkerServiceImpl implements MarkerService {
    private final MarkerRepository markerRepository;
    private final ContainerService containerService;

    @Override
    public Optional<MarkerModel> getMarkerById(String markerId) {
        return markerRepository.findById(markerId).map(ServiceLayerMapper.I::toMarkerModel);
    }

    @Override
    public List<MarkerModel> getAllMarkers() {
        return markerRepository.findAll().stream()
                .map(ServiceLayerMapper.I::toMarkerModel).collect(Collectors.toList());
    }

    @Override
    public List<MarkerModel> getAllVisibleMarkers() {
        return containerService.getAllVisibleContainers().stream().map(ContainerModel::getMarker)
                .collect(Collectors.toList());
    }

    @Override
    public MarkerModel saveMarker(MarkerModel modelToSave) {
        return ServiceLayerMapper.I.toMarkerModel(
                markerRepository.save(
                        ServiceLayerMapper.I.toMarkerEntity(modelToSave)
                ));
    }
}
