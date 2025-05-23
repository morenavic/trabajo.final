package usuario.usecases;

import com.backend.usuario.exceptions.EmailYaVerificadoException;
import com.backend.usuario.exceptions.TokenInvalidoException;
import com.backend.usuario.models.*;
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
    void verificarEmail_TokenValido_EstadoActualizado() {
        // Arrange
        String token = "unTokenValido";
        Usuario usuario = Usuario.instancia("Morena Gallardo", carrera, "more@gmail.com", "Password!123");
        VerificarEmailUseCase verificarEmailUseCase = new VerificarEmailUseCase(iUsuarioRepositorio);

        when(iUsuarioRepositorio.buscarUsuarioPorTokenVerificacion(token)).thenReturn(Optional.of(usuario));
        //Act
        boolean resultado = verificarEmailUseCase.verificarEmail(token);
        // Assert
        Assertions.assertEquals(Estado.Activo,usuario.getEstado());
    }

    @Test
    void verificarEmail_TokenInvalido_TokenInvalidoException() {
        // Arrange
        String token = "unTokenInvalido";
        VerificarEmailUseCase verificarEmailUseCase = new VerificarEmailUseCase(iUsuarioRepositorio);

        // Simulamos que no se encuentra el usuario por el token
        when(iUsuarioRepositorio.buscarUsuarioPorTokenVerificacion(token)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(TokenInvalidoException.class, () -> {
            verificarEmailUseCase.verificarEmail(token);
        });
    }

    @Test
    void verificarEmail_EmailYaVerificado_EmailYaVerificadoException() {
        // Arrange
        String token = "tokenValido";
        Usuario usuario = Usuario.instancia("Morena", carrera, "more@gmail.com", "Password!123");
        usuario.setEmailVerificado(true);
        when(iUsuarioRepositorio.buscarUsuarioPorTokenVerificacion(token)).thenReturn(Optional.of(usuario));

        VerificarEmailUseCase verificarEmailUseCase = new VerificarEmailUseCase(iUsuarioRepositorio);

        // Act-Assert
        Assertions.assertThrows(EmailYaVerificadoException.class, () -> {
            verificarEmailUseCase.verificarEmail(token);
        });
    }

}
