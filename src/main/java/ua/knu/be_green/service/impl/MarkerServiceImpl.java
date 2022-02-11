package ua.knu.be_green.service.impl;

import ua.knu.be_green.persistence.repository.MarkerRepository;
import ua.knu.be_green.service.api.MarkerService;
import ua.knu.be_green.service.mapper.ServiceLayerMapper;
import ua.knu.be_green.service.model.MarkerModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarkerServiceImpl implements MarkerService {
    private final MarkerRepository markerRepository;

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
    public MarkerModel saveMarker(MarkerModel modelToSave) {
        return ServiceLayerMapper.I.toMarkerModel(
                markerRepository.save(
                        ServiceLayerMapper.I.toMarkerEntity(modelToSave)
                ));
    }
}
