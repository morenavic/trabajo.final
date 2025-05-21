package com.backend.jpa.adapter;

import com.backend.jpa.entities.CarreraJpaEntity;
import com.backend.jpa.entities.UsuarioJpaEntity;
import com.backend.jpa.mapper.CarreraJpaMapper;
import com.backend.jpa.mapper.UsuarioJpaMapper;
import com.backend.jpa.repository.CarreraJpaRepository;
import com.backend.jpa.repository.UsuarioJpaRepository;
import com.backend.usuario.models.Estado;
import com.backend.usuario.models.Usuario;
import com.backend.usuario.repositories.IUsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioRepositorioImpl implements IUsuarioRepositorio {

    // Inyectamos las interfaces
    private final UsuarioJpaRepository usuarioJpaRepository;
    private final CarreraJpaRepository carreraJpaRepository; // Necesitamos el repo de Carrera también
    private final UsuarioJpaMapper usuarioJpaMapper;
    private final CarreraJpaMapper carreraJpaMapper;


    // Constructor para la inyección de dependencias por Spring.
    public UsuarioRepositorioImpl(UsuarioJpaRepository usuarioJpaRepository, CarreraJpaRepository carreraJpaRepository, UsuarioJpaMapper usuarioJpaMapper, CarreraJpaMapper carreraJpaMapper) {
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.carreraJpaRepository = carreraJpaRepository;
        this.usuarioJpaMapper = usuarioJpaMapper;
        this.carreraJpaMapper = carreraJpaMapper;
    }

    @Override
    public boolean existeUsuario(String email) {
        return usuarioJpaRepository.findByEmail(email).isPresent();
    }

    @Override
    @Transactional
    public boolean guardarUsuario(Usuario usuario) {
        // Obtener la carrera asociada
        Optional<CarreraJpaEntity> carreraJpa = carreraJpaRepository.findById(usuario.getCarrera().getIdCarrera());

        if (carreraJpa.isEmpty()) {
            throw new IllegalArgumentException("Carrera no encontrada.");
        }

        // Convertimos el objeto Usuario a entidad JPA incluyendo la carrera
        UsuarioJpaEntity usuarioEntidad = usuarioJpaMapper.aEntidadJpa(usuario);
        usuarioEntidad.setCarrera(carreraJpa.get()); // asignamos la carrera existente

        // Guardamos el usuario en la base de datos
        UsuarioJpaEntity entidadGuardada = usuarioJpaRepository.save(usuarioEntidad);

        // Si es nuevo, actualizamos el ID en el dominio
        if (entidadGuardada.getIdUsuario() != null) {
            usuario.setIdUsuario(entidadGuardada.getIdUsuario());
        }

        return entidadGuardada.getIdUsuario() != null;
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorTokenVerificacion(String token) {
        return usuarioJpaRepository.findByTokenVerificacion(token)
                .map(usuarioJpaMapper::aEntidadDominio);
    }

    @Override
    public boolean actualizarEstadoUsuario(Integer id, Estado estado) {
        Optional<UsuarioJpaEntity> usuarioOptional = usuarioJpaRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            UsuarioJpaEntity usuarioJpaEntity = usuarioOptional.get();
            usuarioJpaEntity.setEstado(estado); // Cambia el estado (ej: ACTIVO, INACTIVO, etc.)
            usuarioJpaRepository.save(usuarioJpaEntity); // Guarda el cambio en la BD
            return true;
        }
        return false; // Si no lo encuentra
    }

    @Override
    public boolean limpiarTokenVerificacion(Integer id, String token) {
        Optional<UsuarioJpaEntity> usuarioOptional = usuarioJpaRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            UsuarioJpaEntity usuarioJpaEntity = usuarioOptional.get();
            // Verifica que el token coincida o sea null (opcional pero recomendable)
            if (token == null || token.equals(usuarioJpaEntity.getTokenVerificacion())) {
                usuarioJpaEntity.setTokenVerificacion(null); // Limpia el token
                usuarioJpaRepository.save(usuarioJpaEntity); // Guarda
                return true;
            }
        }
        return false; // No encontrado o token inválido
    }


}
