package com.backend.rest.exceptions;

import com.backend.usuario.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {

    // Logger para registrar los errores
    private static final Logger logger = LoggerFactory.getLogger(ManejadorGlobalExcepciones.class);

    @ExceptionHandler(UsuarioExisteException.class)
    public ResponseEntity<String> handleUsuarioExisteException(UsuarioExisteException ex, WebRequest request) {
        logger.warn("Conflicto de negocio (UsuarioExisteException): " + ex.getMessage() + " - URL: " + request.getDescription(false));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
    }

    @ExceptionHandler({DatosIncompletosException.class, DatosInvalidosException.class, PasswordInseguraException.class})
    public ResponseEntity<String> handleBadRequestExceptions(RuntimeException ex, WebRequest request) {
        logger.warn("Petición inválida (Bad Request Exception): " + ex.getMessage() + " - URL: " + request.getDescription(false));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
    }

    @ExceptionHandler(UsuarioNoExisteException.class)
    public ResponseEntity<String> handleUsuarioNoExisteException(UsuarioNoExisteException ex, WebRequest request) {
        logger.warn("Recurso no encontrado (UsuarioNoExisteException): " + ex.getMessage() + " - URL: " + request.getDescription(false));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // 404 Not Found
    }

    @ExceptionHandler(ErrorAlRegistrarUsuarioException.class)
    public ResponseEntity<String> handleErrorAlRegistrarUsuarioException(ErrorAlRegistrarUsuarioException ex, WebRequest request) {
        logger.error("Error interno al registrar usuario (ErrorAlRegistrarUsuarioException): {} - URL: {}", ex.getMessage(), request.getDescription(false), ex);
        return new ResponseEntity<>("Error interno al registrar usuario. Por favor, intente de nuevo.", HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        logger.error("Error de integridad de datos (DataIntegrityViolationException): " + ex.getMessage() + " - URL: " + request.getDescription(false), ex);
        String message = "Conflicto de datos. Es posible que el email ya esté registrado o haya un problema de unicidad.";
        return new ResponseEntity<>(message, HttpStatus.CONFLICT); // Generalmente 409 para unicidad
    }

    @ExceptionHandler(ErrorDePersistenciaException.class)
    public ResponseEntity<String> handleErrorDePersistenciaException(ErrorDePersistenciaException ex, WebRequest request) {
        logger.error("Error de persistencia en la base de datos (ErrorDePersistenciaException): " + ex.getMessage() + " - URL: " + request.getDescription(false), ex);
        return new ResponseEntity<>("Ocurrió un problema en la base de datos. Por favor, intente más tarde.", HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    @ExceptionHandler(RuntimeException.class) // Captura todas las RuntimeException genéricas
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex, WebRequest request) {
        logger.error("Error inesperado del sistema (RuntimeException): " + ex.getMessage() + " - URL: " + request.getDescription(false), ex);
        return new ResponseEntity<>("Ocurrió un error interno inesperado.", HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
    }

    @ExceptionHandler(Exception.class)
    // Captura todas las Exception (incluyendo RuntimeException, pero se ejecutará si ninguna de las anteriores coincide)
    public ResponseEntity<String> handleGenericException(Exception ex, WebRequest request) {
        logger.error("Error muy inesperado del sistema (Generic Exception): " + ex.getMessage() + " - URL: " + request.getDescription(false), ex);
        return new ResponseEntity<>("Ocurrió un error general del servidor. Contacte al administrador.", HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
    }
}

