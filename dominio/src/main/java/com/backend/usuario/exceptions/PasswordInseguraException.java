package com.backend.usuario.exceptions;

public class PasswordInseguraException extends RuntimeException {
    public PasswordInseguraException(String mensaje) {
        super(mensaje);
    }
}
