package com.backend.usuario.exceptions;

public class ErrorDePersistenciaException extends RuntimeException {
    public ErrorDePersistenciaException(String mensaje) {
        super(mensaje);
    }
}
