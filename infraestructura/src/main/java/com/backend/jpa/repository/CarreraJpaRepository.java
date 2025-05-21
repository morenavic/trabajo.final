package com.backend.jpa.repository;

import com.backend.jpa.entities.CarreraJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarreraJpaRepository extends JpaRepository<CarreraJpaEntity, Integer> {
    Optional<CarreraJpaEntity> findByNombreCarrera(String nombreCarrera);
}
