package com.backend.usuario.usecases;

import com.backend.usuario.exceptions.UsuarioNoExisteException;
import com.backend.usuario.inputs.IVerificarEmailInput;
import com.backend.usuario.models.Estado;
import com.backend.usuario.models.Usuario;
import com.backend.usuario.repositories.IUsuarioRepositorio;

import java.util.Optional;

public class VerificarEmailUseCase implements IVerificarEmailInput {

    private final IUsuarioRepositorio iUsuarioRepositorio;

    public VerificarEmailUseCase(IUsuarioRepositorio iUsuarioRepositorio) {
        this.iUsuarioRepositorio = iUsuarioRepositorio;
    }

    @Override
    public boolean verificarEmail(String token) {
        Optional<Usuario> usuarioOptional = iUsuarioRepositorio.buscarUsuarioPorTokenVerificacion(token);

        if (usuarioOptional.isEmpty()) {
            throw new UsuarioNoExisteException("No existe un usuario con ese token.");
        }

        Usuario usuario = usuarioOptional.get();
        Integer idUsuario = usuario.getIdUsuario();
        boolean estadoActualizado = iUsuarioRepositorio.actualizarEstadoUsuario(idUsuario, Estado.Activo);
        boolean tokenLimpio = iUsuarioRepositorio.limpiarTokenVerificacion(idUsuario, null);

        return estadoActualizado && tokenLimpio;
    }
}
