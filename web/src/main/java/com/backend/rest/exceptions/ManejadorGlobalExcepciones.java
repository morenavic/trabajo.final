package com.backend.rest.exceptions;

import com.backend.usuario.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {

    //Excepciones que retornan -> HttpStatus.CONFLICT
    @ExceptionHandler({UsuarioExisteException.class,
            EmailYaVerificadoException.class})
    public ResponseEntity<String> manejarDatosExistentesException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    //Excepciones que retornan -> HttpStatus.BAD_REQUEST
    @ExceptionHandler({DatosIncompletosException.class,
            DatosInvalidosException.class,
            PasswordInseguraException.class,
            TokenInvalidoException.class})
    public ResponseEntity<String> manejarBadRequestExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    //Excepción que retorna -> HttpStatus.NOT_FOUND
    @ExceptionHandler(UsuarioNoExisteException.class)
    public ResponseEntity<String> manejarUsuarioNoExisteException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    //Excepciones que retornan -> HttpStatus.INTERNAL_SERVER_ERROR
    @ExceptionHandler({ErrorAlRegistrarUsuarioException.class,
            ErrorDePersistenciaException.class})
    public ResponseEntity<String> manejarErrorAlPersistirDatosException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}

