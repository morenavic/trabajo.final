package com.backend.usuario.models;

import com.backend.usuario.exceptions.DatosIncompletosException;
import com.backend.usuario.exceptions.DatosInvalidosException;

import java.util.regex.Pattern;

public class Carrera {
    private Integer idCarrera;
    private String nombreCarrera;

    // Constructor para instanciaExistente()
    private Carrera(Integer idCarrera, String nombreCarrera) {
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
    }

    // Constructor para instancia()
    private Carrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public static Carrera instancia(String nombreCarrera) throws DatosIncompletosException, DatosInvalidosException {

        // Validación de nombre nulo o vacío
        if (nombreCarrera == null || nombreCarrera.trim().isBlank()) {
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

    // Nuevo metodo para rehidratar una carrera existente desde la base de datos
    // Este se usará cuando la carrera ya tiene un ID.
    public static Carrera instanciaExistente(Integer idCarrera, String nombreCarrera) throws DatosIncompletosException, DatosInvalidosException {
        if (idCarrera == null) {
            throw new DatosIncompletosException("El ID de la carrera es obligatorio para una instancia existente.");
        }
        return new Carrera(idCarrera, nombreCarrera);
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

}
