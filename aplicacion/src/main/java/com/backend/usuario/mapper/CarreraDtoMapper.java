package com.backend.usuario.mapper;

import com.backend.usuario.dtos.CarreraRequest;
import com.backend.usuario.models.Carrera;
import com.backend.usuario.exceptions.DatosIncompletosException;
import com.backend.usuario.exceptions.DatosInvalidosException;

public class CarreraDtoMapper {

    public CarreraRequest aDto(Carrera carrera){
        return new CarreraRequest(
                carrera.getIdCarrera(),
                carrera.getNombreCarrera()
        );
    }

    public Carrera aEntidadDominio(CarreraRequest carreraRequest) throws DatosIncompletosException, DatosInvalidosException {
        //Si el request tiene un ID, usamos instanciaExistente
        if (carreraRequest.getIdCarrera() != null) {
            return Carrera.instanciaExistente(
                    carreraRequest.getIdCarrera(),
                    carreraRequest.getNombreCarrera()
            );
        } else {
            //Si el request NO tiene ID, usamos instancia.
            return Carrera.instancia(
                    carreraRequest.getNombreCarrera()
            );
        }
    }
}