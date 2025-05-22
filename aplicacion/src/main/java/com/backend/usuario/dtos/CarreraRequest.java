package com.backend.usuario.dtos;


public class CarreraRequest {
    private Integer idCarrera;
    private String nombreCarrera;

    // Constructor sin argumentos (si es necesario para deserializar)
    public CarreraRequest() {
    }

    // Constructor con todos los argumentos
    public CarreraRequest(Integer idCarrera, String nombreCarrera) {
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
    }

    // Getters
    public Integer getIdCarrera() {
        return idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }
}
