package ua.knu.beGreen.persistence.repository;

import ua.knu.beGreen.persistence.entity.ContainerEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends JpaRepository<ContainerEntity, String> {
    Optional<ContainerEntity> findByAddress(String address);

    List<ContainerEntity> findByIsBeSeenTrue();
}
