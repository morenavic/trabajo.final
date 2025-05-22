package com.backend.usuario.mapper;

import com.backend.usuario.dtos.UsuarioRequest;
import com.backend.usuario.models.Carrera;
import com.backend.usuario.models.Usuario;

public class UsuarioDtoMapper {

    private final CarreraDtoMapper carreraDtoMapper;

    //Constructor para inyectar CarreraDtoMapper
    public UsuarioDtoMapper(CarreraDtoMapper carreraDtoMapper) {
        this.carreraDtoMapper = carreraDtoMapper;
    }

    public UsuarioRequest aDto(Usuario usuario){
        return new UsuarioRequest(
                usuario.getIdUsuario(),
                usuario.getNombreCompleto(),
                carreraDtoMapper.aDto(usuario.getCarrera()),
                usuario.getEmail(),
                usuario.getEmailVerificado(),
                usuario.getPassword(),
                usuario.getTokenVerificacion(),
                usuario.getRol(),
                usuario.getEstado(),
                usuario.getImagenPerfil(),
                usuario.getFechaAlta(),
                usuario.getFechaBaja()
        );
    }

    public Usuario aEntidadDominio(UsuarioRequest usuarioRequest){

        if (usuarioRequest == null) {
            return null;
        }

        Carrera carreraDomain = carreraDtoMapper.aEntidadDominio(usuarioRequest.getCarrera());

        if (usuarioRequest.getIdUsuario() == null) {
            //Es un nuevo usuario (registro)
            return Usuario.instancia(
                    usuarioRequest.getNombreCompleto(),
                    carreraDomain,
                    usuarioRequest.getEmail(),
                    usuarioRequest.getPassword()
            );
        } else {
            //Es un usuario existente (ej. para actualización desde DTO)
            return Usuario.instanciaExistente(
                    usuarioRequest.getIdUsuario(),
                    usuarioRequest.getNombreCompleto(),
                    carreraDomain,
                    usuarioRequest.getEmail(),
                    usuarioRequest.getPassword(),
                    usuarioRequest.getRol(),
                    usuarioRequest.getEstado(),
                    usuarioRequest.isEmailVerificado(),
                    usuarioRequest.getTokenVerificacion(),
                    usuarioRequest.getImagenPerfil(),
                    usuarioRequest.getFechaAlta(),
                    usuarioRequest.getFechaBaja()
            );
        }
    }
}
