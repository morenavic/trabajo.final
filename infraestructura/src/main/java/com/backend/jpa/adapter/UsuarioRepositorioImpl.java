package com.backend.jpa.adapter;

import com.backend.jpa.entities.CarreraJpaEntity;
import com.backend.jpa.entities.UsuarioJpaEntity;
import com.backend.jpa.mapper.CarreraJpaMapper;
import com.backend.jpa.mapper.UsuarioJpaMapper;
import com.backend.jpa.repository.CarreraJpaRepository;
import com.backend.jpa.repository.UsuarioJpaRepository;
import com.backend.usuario.models.Carrera;
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


    // Constructor para la inyección de dependencias.
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
    public boolean guardarUsuario(Usuario usuarioDominio) { // Renombrado a usuarioDominio para claridad
        Carrera carreraDominio = usuarioDominio.getCarrera();
        CarreraJpaEntity carreraJpaEntity = null; //Inicializado a null

        if (carreraDominio != null) {
            //Si la carrera NO viene con un ID, es un error (las carreras son preexistentes)
            if (carreraDominio.getIdCarrera() == null) {
                throw new IllegalArgumentException("El ID de la carrera es obligatorio. Las carreras deben ser pre-existentes.");
            }

            //Si la carrera viene con un ID, intentamos buscarla en la DB
            Optional<CarreraJpaEntity> existingCarreraJpa = carreraJpaRepository.findById(carreraDominio.getIdCarrera());

            if (existingCarreraJpa.isPresent()) {
                carreraJpaEntity = existingCarreraJpa.get(); // Asignamos la entidad JPA encontrada
            } else {
                // Si el ID de la carrera NO existe en la DB, lanzamos una excepción.
                throw new IllegalArgumentException("Carrera con ID " + carreraDominio.getIdCarrera() + " no encontrada.");
            }
        }

        // Convertimos el Usuario de Dominio a UsuarioJpaEntity
        UsuarioJpaEntity usuarioJpaEntity = usuarioJpaMapper.aEntidadJpa(usuarioDominio);

        // Aseguramos que la CarreraJpaEntity esté asignada al UsuarioJpaEntity
        usuarioJpaEntity.setCarrera(carreraJpaEntity);

        //Guardamos el UsuarioJpaEntity en la base de datos
        UsuarioJpaEntity entidadGuardada = usuarioJpaRepository.save(usuarioJpaEntity);

        //Si el usuario es nuevo (idUsuario se generó), actualizamos el ID en el objeto de dominio
        if (entidadGuardada.getIdUsuario() != null) {
            usuarioDominio.setIdUsuario(entidadGuardada.getIdUsuario());
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
