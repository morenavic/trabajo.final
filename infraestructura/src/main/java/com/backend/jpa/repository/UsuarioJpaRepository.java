package com.backend.jpa.repository;

import com.backend.jpa.entities.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, Integer> {
    Optional<UsuarioJpaEntity> findByEmail(String email);
    Optional<UsuarioJpaEntity> findByTokenVerificacion(String tokenVerificacion);
}
