package com.backend.usuario.usecases;

import com.backend.usuario.exceptions.ErrorAlRegistrarUsuarioException;
import com.backend.usuario.exceptions.UsuarioExisteException;
import com.backend.usuario.inputs.IRegistrarUsuarioInput;
import com.backend.usuario.models.Usuario;
import com.backend.usuario.repositories.IUsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrarUsuarioUseCase implements IRegistrarUsuarioInput {

    private final IUsuarioRepositorio iUsuarioRepositorio;

    public RegistrarUsuarioUseCase(IUsuarioRepositorio iUsuarioRepositorio) {
        this.iUsuarioRepositorio = iUsuarioRepositorio;
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) throws UsuarioExisteException {
        // 1. Verificar si el usuario ya existe con el mismo email
        if (iUsuarioRepositorio.existeUsuario(usuario.getEmail())) {
            throw new UsuarioExisteException("Ya existe un usuario con ese email");
        }
        // 2. Generar un token único de verificación y asignarlo a la entidad Usuario.
        String tokenVerificacion = UUID.randomUUID().toString();
        usuario.setTokenVerificacion(tokenVerificacion);

        // 3. Guardar el usuario con el token asignado.
        boolean guardadoExitoso = iUsuarioRepositorio.guardarUsuario(usuario);

        // 4. Si el guardado no fue exitoso, lanzar una excepción.
        if (!guardadoExitoso) {
            throw new ErrorAlRegistrarUsuarioException("Error al registrar el usuario en la base de datos.");
        }
        return true;
    }
}
