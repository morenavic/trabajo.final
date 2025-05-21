package com.backend.usuario.exceptions;

public class DatosIncompletosException extends RuntimeException {
    public DatosIncompletosException(String mensaje) {
        super(mensaje);
    }
}
