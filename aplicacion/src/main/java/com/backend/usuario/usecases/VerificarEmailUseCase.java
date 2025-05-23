package com.backend.usuario.usecases;

import com.backend.usuario.exceptions.EmailYaVerificadoException;
import com.backend.usuario.exceptions.ErrorDePersistenciaException;
import com.backend.usuario.exceptions.TokenInvalidoException;
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

        //Validar si el toke es válido
        if (usuarioOptional.isEmpty()) {
            throw new TokenInvalidoException("El TOKEN no es válido o ya fue usado/expiró.");
        }

        Usuario usuario = usuarioOptional.get();

        //Validar si el correo ya está verificado
        if (usuario.getEmailVerificado()) {
            throw new EmailYaVerificadoException("El correo electrónico ya ha sido verificado.");
        }

        //Se modifica el objeto de dominio
        usuario.setEmailVerificado(true);
        usuario.setTokenVerificacion(null);
        usuario.setEstado(Estado.Activo);

        // Se guarda el objeto de dominio completo.
        return iUsuarioRepositorio.guardarUsuario(usuario);
    }
}