package com.backend.usuario.exceptions;

public class ErrorAlRegistrarUsuarioException extends RuntimeException {
    public ErrorAlRegistrarUsuarioException(String mensaje) {
        super(mensaje);
    }
}
