package com.backend.rest.controllers;

import com.backend.usuario.dtos.RegistroUsuarioRequest;
import com.backend.usuario.inputs.IRegistrarUsuarioInput;
import com.backend.usuario.models.Carrera;
import com.backend.usuario.models.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final IRegistrarUsuarioInput registrarUsuarioInput;

    public UsuarioController(IRegistrarUsuarioInput registrarUsuarioInput) {
        this.registrarUsuarioInput = registrarUsuarioInput;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody RegistroUsuarioRequest request) {

        // Mapeo DTO → Dominio
        Carrera carrera = Carrera.instancia(request.getCarrera());
        Usuario usuario = Usuario.instancia(request.getNombreCompleto(), carrera, request.getEmail(), request.getPassword());

        // Ejecutar caso de uso (sin try-catch, porque las excepciones van al Handler)
        registrarUsuarioInput.registrarUsuario(usuario);

        // Si no hubo excepción, asumimos éxito
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado exitosamente. Se ha enviado un correo de verificación.");
    }
}
