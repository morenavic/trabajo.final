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
        //Si la entidad JPA tiene un ID, usamos instanciaExistente
        if (carreraJpaEntity.getIdCarrera() != null) {
            return Carrera.instanciaExistente(
                    carreraJpaEntity.getIdCarrera(),
                    carreraJpaEntity.getNombreCarrera()
            );
        } else {
            //Si la entidad JPA no tiene un ID, usamos instanciaExistente
            return Carrera.instancia(
                    carreraJpaEntity.getNombreCarrera()
            );
        }
    }
}
