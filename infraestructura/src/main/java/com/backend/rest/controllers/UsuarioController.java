package com.backend.rest.controllers;

import com.backend.usuario.dtos.UsuarioRequest;
import com.backend.usuario.inputs.IRegistrarUsuarioInput;
import com.backend.usuario.mapper.UsuarioDtoMapper;
import com.backend.usuario.models.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final IRegistrarUsuarioInput registrarUsuarioInput;
    private final UsuarioDtoMapper userDtoMapper; // Inyectamos el mapper de Usuario

    public UsuarioController(IRegistrarUsuarioInput registrarUsuarioInput, UsuarioDtoMapper userDtoMapper) {
        this.registrarUsuarioInput = registrarUsuarioInput;
        this.userDtoMapper = userDtoMapper;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody UsuarioRequest request) {

        //Mapeo DTO a Dominio
        Usuario usuario = userDtoMapper.aEntidadDominio(request);

        registrarUsuarioInput.registrarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado exitosamente. Se ha enviado un correo de verificación.");
    }
}
