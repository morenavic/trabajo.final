package com.backend.usuario.mapper;

import com.backend.usuario.dtos.UsuarioRequest;
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
        return Usuario.instancia(
                usuarioRequest.getNombreCompleto(),
                carreraDtoMapper.aEntidadDominio(usuarioRequest.getCarrera()),
                usuarioRequest.getEmail(),
                usuarioRequest.getPassword()
        );
    }
}
