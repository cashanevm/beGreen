package ua.knu.beGreen.service.api;

import ua.knu.beGreen.service.model.ContainerModel;

import java.util.List;
import java.util.Optional;

public interface ContainerService {
    Optional<ContainerModel> getContainerById(String containerId);

    ContainerModel save(ContainerModel model);

    Optional<ContainerModel> findByAddress(String address);

    List<ContainerModel> getAllVisibleContainers();

    void add(ContainerModel model);

    void enableContainer(String containerId);

    void disableContainer(String containerId);

    void delete(String id);
}
