package ua.knu.be_green.persistence.repository;

import ua.knu.be_green.persistence.entity.MarkerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkerRepository extends JpaRepository<MarkerEntity, String> {
}
