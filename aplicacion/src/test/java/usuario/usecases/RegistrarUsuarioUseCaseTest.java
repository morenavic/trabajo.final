package usuario.usecases;

import com.backend.usuario.exceptions.ErrorAlRegistrarUsuarioException;
import com.backend.usuario.exceptions.UsuarioExisteException;
import com.backend.usuario.models.Carrera;
import com.backend.usuario.models.Estado;
import com.backend.usuario.models.Usuario;
import com.backend.usuario.repositories.IUsuarioRepositorio;
import com.backend.usuario.usecases.RegistrarUsuarioUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrarUsuarioUseCaseTest {

    @Mock
    IUsuarioRepositorio iUsuarioRepositorio;

    private final Carrera carrera = Carrera.instancia("Tecnicatura Universitaria en Desarrollo de Aplicaciones Web");

    @Test
    void registrarUsuario_UsuarioNoExiste_UsuarioCreado() {
        // Arrange
        Usuario usuario = Usuario.instancia("Morena Gallardo", carrera,"more@gmail.com","Password!123");
        RegistrarUsuarioUseCase registrarUsuarioUseCase = new RegistrarUsuarioUseCase(iUsuarioRepositorio);

        when(iUsuarioRepositorio.existeUsuario(usuario.getEmail())).thenReturn(false);
        when(iUsuarioRepositorio.guardarUsuario(usuario)).thenReturn(true);

        // Act
        boolean resultado = registrarUsuarioUseCase.registrarUsuario(usuario);

        // Assert
        Assertions.assertTrue(resultado);
    }

    @Test
    void registrarUsuario_UsuarioExiste_UsuarioExisteException() {
        // Arrange
        Usuario usuario = Usuario.instancia("Morena Gallardo", carrera,"more@gmail.com","Password!123");
        RegistrarUsuarioUseCase registrarUsuarioUseCase = new RegistrarUsuarioUseCase(iUsuarioRepositorio);

        when(iUsuarioRepositorio.existeUsuario(usuario.getEmail())).thenReturn(true);
        // Act - Assert
        Assertions.assertThrows(UsuarioExisteException.class, () -> registrarUsuarioUseCase.registrarUsuario(usuario));
    }

    @Test
    void registrarUsuario_UsuarioNoExiste_ErrorAlRegistrarUsuarioException() {
        // Arrange
        Usuario usuario = Usuario.instancia("Morena Gallardo", carrera,"more@gmail.com","Password!123");
        RegistrarUsuarioUseCase registrarUsuarioUseCase = new RegistrarUsuarioUseCase(iUsuarioRepositorio);

        when(iUsuarioRepositorio.existeUsuario(usuario.getEmail())).thenReturn(false);
        when(iUsuarioRepositorio.guardarUsuario(usuario)).thenReturn(false);

        // Act-Assert
        Assertions.assertThrows(ErrorAlRegistrarUsuarioException.class,
                () -> registrarUsuarioUseCase.registrarUsuario(usuario));
    }

    @Test
    void registrarUsuario_UsuarioNoExiste_TokenGeneradoYGuardadoEnEntidad() {
        // Arrange
        Usuario usuario = Usuario.instancia("Morena Gallardo", carrera,"more@gmail.com","Password!123");

        RegistrarUsuarioUseCase registrarUsuarioUseCase = new RegistrarUsuarioUseCase(iUsuarioRepositorio);

        when(iUsuarioRepositorio.existeUsuario(usuario.getEmail())).thenReturn(false);
        when(iUsuarioRepositorio.guardarUsuario(usuario)).thenReturn(true);

        // Act
        boolean resultado = registrarUsuarioUseCase.registrarUsuario(usuario);

        // Assert
        Assertions.assertTrue(resultado);

        // Verificar que el token fue generado y asignado a la entidad
        Assertions.assertNotNull(usuario.getTokenVerificacion());
        Assertions.assertEquals(Estado.Pendiente, usuario.getEstado());
    }

}
