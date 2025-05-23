package com.backend.rest.controllers;

import com.backend.usuario.dtos.UsuarioRequest;
import com.backend.usuario.exceptions.EmailYaVerificadoException;
import com.backend.usuario.exceptions.TokenInvalidoException;
import com.backend.usuario.inputs.IRegistrarUsuarioInput;
import com.backend.usuario.inputs.IVerificarEmailInput;
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
    private final IVerificarEmailInput iVerificarEmailInput;

    public UsuarioController(IRegistrarUsuarioInput registrarUsuarioInput, UsuarioDtoMapper userDtoMapper, IVerificarEmailInput iVerificarEmailInput) {
        this.registrarUsuarioInput = registrarUsuarioInput;
        this.userDtoMapper = userDtoMapper;
        this.iVerificarEmailInput = iVerificarEmailInput;
    }

    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registrarUsuario(@RequestBody UsuarioRequest request) {

        //Mapeo DTO a Dominio
        Usuario usuario = userDtoMapper.aEntidadDominio(request);

        registrarUsuarioInput.registrarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado exitosamente. Se ha enviado un correo de verificación.");
    }

    @GetMapping("/verificar-email")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> verificarEmail(@RequestParam String token) throws TokenInvalidoException, EmailYaVerificadoException {

        boolean verificado = iVerificarEmailInput.verificarEmail(token);

        if (verificado) {
            return new ResponseEntity<>("¡Correo electrónico verificado exitosamente!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se pudo verificar el correo electrónico.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
