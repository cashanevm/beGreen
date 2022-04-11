package ua.knu.beGreen.persistence.repository;

import ua.knu.beGreen.persistence.entity.MarkerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkerRepository extends JpaRepository<MarkerEntity, String> {
}
