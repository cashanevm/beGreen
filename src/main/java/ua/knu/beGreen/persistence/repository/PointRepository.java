package ua.knu.beGreen.persistence.repository;

import ua.knu.beGreen.persistence.entity.MarkerEntity;
import ua.knu.beGreen.persistence.entity.PointEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, String> {
    List<PointEntity> findByContainerId(String containerId);
}
