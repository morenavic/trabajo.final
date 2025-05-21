package com.backend.usuario.exceptions;

public class UsuarioNoExisteException extends RuntimeException {
    public UsuarioNoExisteException(String mensaje) {
        super(mensaje);
    }
}
