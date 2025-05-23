package com.backend.usuario.repositories;

import com.backend.usuario.models.Estado;
import com.backend.usuario.models.Usuario;

import java.util.Optional;

public interface IUsuarioRepositorio {
    boolean existeUsuario(String email);
    boolean guardarUsuario(Usuario usuario);
    Optional<Usuario> buscarUsuarioPorTokenVerificacion(String token);
}
