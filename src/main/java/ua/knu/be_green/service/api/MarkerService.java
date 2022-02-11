package ua.knu.be_green.service.api;


import ua.knu.be_green.service.model.MarkerModel;

import java.util.List;
import java.util.Optional;

public interface MarkerService {
    /**
     * Get marker by id.
     *
     * @param markerId - marker id
     *
     * @return Optional of MarkerModel
     */
    Optional<MarkerModel> getMarkerById(String markerId);

    /**
     * Get all markers.
     *
     * @return List of MarkerModel
     */
    List<MarkerModel> getAllMarkers();

    /**
     * Save marker.
     *
     * @param modelToSave - model to save
     *
     * @return saved model — MarkerModel
     */
    MarkerModel saveMarker(MarkerModel modelToSave);
}
