package ua.knu.be_green.tests.web.controller;

import ua.knu.be_green.service.api.MarkerService;
import ua.knu.be_green.service.model.MarkerModel;
import ua.knu.be_green.web.controller.rest.MarkerController;
import ua.knu.be_green.web.dto.request.MarkerRequestDto;
import ua.knu.be_green.web.dto.response.MarkerResponseDto;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.knu.be_green.service.constants.ApplicationConstants.REST_APPLICATION_PREFIX;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MarkerController.class)
public class MarkerControllerTest {
    private static final String CONTROLLER_PATH = REST_APPLICATION_PREFIX + "/map/marker";

    private static final String GET_MARKER_BY_ID_PATH = CONTROLLER_PATH + "/{id}";
    private static final String GET_ALL_MARKERS_PATH = CONTROLLER_PATH + "/all";
    private static final String SAVE_MARKER_PATH = CONTROLLER_PATH + "";

    private static final String MARKER_ID = "testMarkerId";
    private static final String NAME = "testName";
    private static final String LAT = "testLat";
    private static final String LNG = "testLng";

    @MockBean
    private MarkerService markerService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetMarkerByIdSuccess() throws Exception {
        when(markerService.getMarkerById(MARKER_ID)).thenReturn(Optional.of(
                MarkerModel.builder()
                    .id(MARKER_ID)
                    .name(NAME)
                    .lat(LAT)
                    .lng(LNG)
                    .build())
        );

        MvcResult result = mockMvc.perform(get(GET_MARKER_BY_ID_PATH, MARKER_ID))
                .andExpect(status().isOk()).andReturn();
        MarkerResponseDto actualResponse = objectMapper
                .readValue(result.getResponse().getContentAsString(), MarkerResponseDto.class);

        MarkerResponseDto expectedResponse = MarkerResponseDto.builder()
                .id(MARKER_ID)
                .name(NAME)
                .lat(LAT)
                .lng(LNG)
                .build();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void whenGetAllMarkersThenSuccess() throws Exception {
        when(markerService.getAllMarkers()).thenReturn(List.of());

        mockMvc.perform(get(GET_ALL_MARKERS_PATH))
                .andExpect(status().isOk());
    }

    @Test
    void whenSaveMarkerThenSuccess() throws Exception {
        MarkerRequestDto expectedRequest = MarkerRequestDto.builder()
                .name(NAME)
                .lat(LAT)
                .lng(LNG)
                .build();

        when(markerService.saveMarker(Mockito.any(MarkerModel.class))).thenAnswer(i -> i.getArguments()[0]);

        MvcResult result = mockMvc.perform(post(SAVE_MARKER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedRequest)))
                .andExpect(status().isOk()).andReturn();

        MarkerResponseDto actualResponse = objectMapper
                .readValue(result.getResponse().getContentAsString(), MarkerResponseDto.class);

        MarkerResponseDto expectedResponse = MarkerResponseDto.builder()
                .name(NAME)
                .lat(LAT)
                .lng(LNG)
                .build();

        assertEquals(expectedResponse, actualResponse);
    }
}
