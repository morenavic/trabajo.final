package com.backend.usuario.exceptions;

public class EmailYaVerificadoException extends RuntimeException {
    public EmailYaVerificadoException(String mensaje) {
        super(mensaje);
    }
}
