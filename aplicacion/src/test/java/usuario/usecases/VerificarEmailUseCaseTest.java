package usuario.usecases;

import com.backend.usuario.models.*;
import com.backend.usuario.exceptions.UsuarioNoExisteException;
import com.backend.usuario.repositories.IUsuarioRepositorio;
import com.backend.usuario.usecases.VerificarEmailUseCase;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VerificarEmailUseCaseTest {

    @Mock
    IUsuarioRepositorio iUsuarioRepositorio;

    private final Carrera carrera = Carrera.instancia("Tecnicatura Universitaria en Desarrollo de Aplicaciones Web");

    @Test
    void verificarEmail_TokenValido_UsuarioEncontrado_EstadoActualizado() {
        // Arrange
        String token = "unTokenValido";
        Usuario usuario = Usuario.instancia("Morena Gallardo", carrera, "more@gmail.com", "Password!123");
        VerificarEmailUseCase verificarEmailUseCase = new VerificarEmailUseCase(iUsuarioRepositorio);

        when(iUsuarioRepositorio.buscarUsuarioPorTokenVerificacion(token)).thenReturn(Optional.of(usuario));
        when(iUsuarioRepositorio.actualizarEstadoUsuario(null, Estado.Activo)).thenReturn(true);
        when(iUsuarioRepositorio.limpiarTokenVerificacion(null, null)).thenReturn(true);

        // Act
        boolean resultado = verificarEmailUseCase.verificarEmail(token);

        // Assert
        Assertions.assertTrue(resultado);
    }

    @Test
    void verificarEmail_TokenInvalido_UsuarioNoEncontrado() {
        // Arrange
        String token = "unTokenInvalido";
        VerificarEmailUseCase verificarEmailUseCase = new VerificarEmailUseCase(iUsuarioRepositorio);

        when(iUsuarioRepositorio.buscarUsuarioPorTokenVerificacion(token)).thenReturn(Optional.empty());

        // Act-Assert
        Assertions.assertThrows(UsuarioNoExisteException.class,
                () -> verificarEmailUseCase.verificarEmail(token));
    }

    @Test
    void verificarEmail_TokenValido_UsuarioEncontrado_ErrorAlActualizarEstado() {
        // Arrange
        String token = "unTokenValido";
        Usuario usuario = Usuario.instancia("Morena Gallardo", carrera, "more@gmail.com", "Password!123");
        VerificarEmailUseCase verificarEmailUseCase = new VerificarEmailUseCase(iUsuarioRepositorio);

        when(iUsuarioRepositorio.buscarUsuarioPorTokenVerificacion(token)).thenReturn(Optional.of(usuario));
        when(iUsuarioRepositorio.actualizarEstadoUsuario(null, Estado.Activo)).thenReturn(false);
        when(iUsuarioRepositorio.limpiarTokenVerificacion(null, null)).thenReturn(false);

        // Act
        boolean resultado = verificarEmailUseCase.verificarEmail(token);

        // Assert
        Assertions.assertFalse(resultado);
    }

}
