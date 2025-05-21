package com.backend.jpa.mapper;

import com.backend.jpa.entities.CarreraJpaEntity;
import com.backend.usuario.models.Carrera;
import org.springframework.stereotype.Component;

@Component
public class CarreraJpaMapper {

    public CarreraJpaEntity aEntidadJpa(Carrera carrera) {
        return new CarreraJpaEntity(
                carrera.getNombreCarrera());
    }

    public Carrera aEntidadDominio(CarreraJpaEntity carreraJpaEntity) {
        return Carrera.instancia(carreraJpaEntity.getNombreCarrera());
    }
}
