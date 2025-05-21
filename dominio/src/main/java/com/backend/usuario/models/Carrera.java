package com.backend.usuario.models;

import com.backend.usuario.exceptions.DatosIncompletosException;
import com.backend.usuario.exceptions.DatosInvalidosException;

import java.util.regex.Pattern;

public class Carrera {
    private Integer idCarrera;
    private String nombreCarrera;

    public Carrera(String nombreCarrera) {
        this.idCarrera = null;
        this.nombreCarrera = nombreCarrera;
    }

    public static Carrera instancia(String nombreCarrera) throws DatosIncompletosException, DatosInvalidosException{

        // Validación de nombre nulo o vacío
        if(nombreCarrera == null || nombreCarrera.trim().isBlank()){
            throw new DatosIncompletosException("El nombre de la carrera es obligatorio.");
        }

        // Validación de caracteres especiales en el nombre
        Pattern specialCharacterPattern = Pattern.compile("[^a-zA-Z\\s]"); // Permite letras y espacios
        if (specialCharacterPattern.matcher(nombreCarrera).find()) {
            throw new DatosInvalidosException("El nombre de la carrera no debe contener caracteres especiales.");
        }

        // Validación de números en el nombre
        Pattern numberPattern = Pattern.compile("[0-9]");
        if (numberPattern.matcher(nombreCarrera).find()) {
            throw new DatosInvalidosException("El nombre de la carrera no debe contener números.");
        }

        return new Carrera(nombreCarrera);
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }
}
