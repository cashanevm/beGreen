package ua.knu.beGreen.service.api.impl;

import ua.knu.beGreen.persistence.entity.ContainerEntity;
import ua.knu.beGreen.persistence.repository.ContainerRepository;
import ua.knu.beGreen.service.api.ContainerService;
import ua.knu.beGreen.service.mapper.ServiceLayerMapper;
import ua.knu.beGreen.service.model.ContainerModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContainerServiceImpl implements ContainerService {
    private final ContainerRepository containerRepository;

    @Override
    public Optional<ContainerModel> getContainerById(String containerId) {
        return containerRepository.findById(containerId).map(ServiceLayerMapper.I::toContainerModel);
    }

    @Override
    public ContainerModel save(ContainerModel model) {
        return ServiceLayerMapper.I.toContainerModel(containerRepository
                .save(ServiceLayerMapper.I.toContainerEntity(model)));
    }

    @Override
    public Optional<ContainerModel> findByAddress(String address) {
        return containerRepository.findByAddress(address).map(ServiceLayerMapper.I::toContainerModel);
    }

    @Override
    public List<ContainerModel> getAllVisibleContainers() {
        return containerRepository.findByIsBeSeenTrue().stream()
                .map(ServiceLayerMapper.I::toContainerModel).collect(Collectors.toList());
    }

    @Override
    public void add(ContainerModel model) {
        Optional<ContainerModel> container = this.findByAddress(model.getAddress());

        if (container.isPresent() && !container.get().isBeSeen()) {
            ContainerModel existedModel = container.get();

            int times = existedModel.getAddedUsers() == null ? 0 : existedModel.getAddedUsers();

            if (times > 2) {
                existedModel.setBeSeen(true);
            } else {
                times = times + 1;
                existedModel.setAddedUsers(times);
            }
            this.save(existedModel);
        } else {
            this.save(model);
        }
    }

    @Override
    //404
    public void enableContainer(String containerId) {
        ContainerModel container = this.getContainerById(containerId).get();
        container.setBeSeen(true);
        this.save(container);
    }

    @Override
    //404
    public void disableContainer(String containerId) {
        ContainerModel container = this.getContainerById(containerId).get();
        container.setBeSeen(false);
        this.save(container);
    }

    @Override
    public void delete(String id) {
        Optional<ContainerEntity> container = containerRepository.findById(id);
        if (container.isPresent()) {
            containerRepository.deleteById(id);
        }
    }
}
