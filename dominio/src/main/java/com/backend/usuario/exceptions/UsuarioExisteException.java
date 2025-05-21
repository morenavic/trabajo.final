package com.backend.usuario.exceptions;

public class UsuarioExisteException extends RuntimeException {
    public UsuarioExisteException(String mensaje) {
        super(mensaje);
    }
}
