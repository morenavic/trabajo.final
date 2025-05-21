package com.backend.jpa.mapper;

import com.backend.jpa.entities.UsuarioJpaEntity;
import com.backend.usuario.models.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioJpaMapper {

    private final CarreraJpaMapper carreraJpaMapper;

    public UsuarioJpaMapper(CarreraJpaMapper carreraJpaMapper) {
        this.carreraJpaMapper = carreraJpaMapper;
    }

    public UsuarioJpaEntity aEntidadJpa(Usuario usuario) {
        return new UsuarioJpaEntity(
                usuario.getNombreCompleto(),
                carreraJpaMapper.aEntidadJpa(usuario.getCarrera()),
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

    public Usuario aEntidadDominio(UsuarioJpaEntity usuarioJpaEntity) {
        if (usuarioJpaEntity == null) {
            return null;
        }

        return Usuario.instancia(
                usuarioJpaEntity.getNombreCompleto(),
                carreraJpaMapper.aEntidadDominio(usuarioJpaEntity.getCarrera()),
                usuarioJpaEntity.getEmail(),
                usuarioJpaEntity.getPassword()

        );
    }
}
