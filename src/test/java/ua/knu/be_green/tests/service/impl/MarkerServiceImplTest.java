package ua.knu.be_green.tests.service.impl;

import ua.knu.be_green.persistence.entity.MarkerEntity;
import ua.knu.be_green.persistence.repository.MarkerRepository;
import ua.knu.be_green.service.api.MarkerService;
import ua.knu.be_green.service.impl.MarkerServiceImpl;
import ua.knu.be_green.service.model.MarkerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarkerServiceImplTest {
    private static final String MARKER_ID = "testMarkerId";
    private static final String NAME = "testName";
    private static final String LAT = "testLat";
    private static final String LNG = "testLng";

    private MarkerService markerService;

    @Mock
    private MarkerRepository markerRepository;

    @BeforeEach
    void setUp() {
        markerService = new MarkerServiceImpl(markerRepository);
    }

    @Test
    public void whenGetMarkerByIdThenSuccess() {
        MarkerEntity savedMarker = MarkerEntity.builder()
                .id(MARKER_ID)
                .name(NAME)
                .lat(LAT)
                .lng(LNG)
                .build();

        when(markerRepository.findById(MARKER_ID)).thenReturn(Optional.of(savedMarker));

        MarkerModel expectedModel = MarkerModel.builder()
                .id(MARKER_ID)
                .name(NAME)
                .lat(LAT)
                .lng(LNG)
                .build();

        MarkerModel actualModel = markerService.getMarkerById(MARKER_ID).get();

        assertEquals(expectedModel, actualModel);
    }

    @Test
    public void whenGetMarkerByIdThenFail() {
        when(markerRepository.findById(MARKER_ID)).thenReturn(Optional.empty());

        Optional<MarkerModel> actualOptional = markerService.getMarkerById(MARKER_ID);

        assertTrue(actualOptional.isEmpty());
    }

    @Test
    public void whenGetEmptyListThenSuccess() {
        when(markerRepository.findAll()).thenReturn(List.of());

        List<MarkerModel> actualList = markerService.getAllMarkers();

        assertTrue(actualList.isEmpty());
    }

    @Test
    public void whenGetAllMarkersThenSuccess() {
        int expectedListSize = 3;

        MarkerEntity mockedEntity = Mockito.mock(MarkerEntity.class);
        List<MarkerEntity> savedList = new ArrayList<>(Collections.nCopies(expectedListSize, mockedEntity));

        when(markerRepository.findAll()).thenReturn(savedList);

        List<MarkerModel> actualList = markerService.getAllMarkers();
        assertEquals(expectedListSize, actualList.size());
    }

    @Test
    public void whenSaveMarkerThenSuccess() {
        MarkerModel modelToSave = MarkerModel.builder()
                .id(MARKER_ID)
                .name(NAME)
                .lat(LAT)
                .lng(LNG)
                .build();

        when(markerRepository.save(Mockito.any(MarkerEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        MarkerModel savedModel = markerService.saveMarker(modelToSave);

        assertEquals(modelToSave, savedModel);
    }


}
