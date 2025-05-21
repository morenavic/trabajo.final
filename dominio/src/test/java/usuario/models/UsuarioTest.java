package usuario.models;

import com.backend.usuario.exceptions.DatosIncompletosException;
import com.backend.usuario.exceptions.DatosInvalidosException;
import com.backend.usuario.exceptions.PasswordInseguraException;
import com.backend.usuario.models.Carrera;
import com.backend.usuario.models.Usuario;
import com.backend.usuario.models.Estado;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullSource;


public class UsuarioTest {

    private final Carrera carrera = Carrera.instancia("Tecnicatura Universitaria en Desarrollo de Aplicaciones Web");

    @Test
    void instanciarUsuario_TodosLosAtributos_InstanciaCorrecta(){
        Usuario usuario = Usuario.instancia("Morena Gallardo",carrera,"more@gmail.com","Password!123");

        Assertions.assertEquals("more@gmail.com",usuario.getEmail());
        Assertions.assertEquals(Estado.Pendiente,usuario.getEstado());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",  // Nombre vacío
            "  ",  // Nombre con solo espacios
    })
    @NullSource // Nombre nulo
    void instanciarUsuario_NombreIncompleto_DatosIncompletosException(String nombre){
        Assertions.assertThrows(DatosIncompletosException.class,
                ()->Usuario.instancia(nombre, carrera, "more@gmail.com","password"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Morena!", // Nombre con caracteres especiales
            "Morena123", // Nombre con números
    })
    void instanciarUsuario_NombreFormatoInvalido_DatosInvalidosException(String nombre){
        Assertions.assertThrows(DatosInvalidosException.class,
                ()->Usuario.instancia(nombre, carrera, "more@gmail.com","password"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "", // Email vacío
            " ", // Email con solo espacios
    })
    @NullSource // Email nulo
    void instanciarUsuario_EmailIncompleto_DatosIncompletosException(String email){
        Assertions.assertThrows(DatosIncompletosException.class,
                ()->Usuario.instancia("Morena Gallardo", carrera, email,"password"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "more-email", // Email sin @
            "more@gmail", // Email sin dominio
            "@gmail.com", // Email sin usuario
    })
    void instanciarUsuario_EmailFormatoInvalido_DatosInvalidosException(String email){
        Assertions.assertThrows(DatosInvalidosException.class,
                ()->Usuario.instancia("Morena Gallardo", carrera, email,"password"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "", // Contraseña vacía
            " ", // Contraseña con solo espacios
            "corta" // Contraseña con menos de 8 caracteres
    })
    @NullSource // Contraseña nula
    void instanciarUsuario_PasswordIncompleta_DatosIncompletosException(String password){
        Assertions.assertThrows(DatosIncompletosException.class,
                ()->Usuario.instancia("Morena Gallardo", carrera,"more@gmail.com", password));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "password",   // Contraseña con solo minúsculas
            "PASSWORD",   // Contraseña con solo mayúsculas
            "12345678",   // Contraseña con solo números
            "!!!!!!!!",   // Contraseña con solo caracteres especiales
            "Password123", // Contraseña sin caracter especial
            "Password!",   // Contraseña sin número
            "Password! 123" // Contraseña contiene espacios
    })
    void instanciarUsuario_PasswordInsegura_PasswordInseguraException(String password){
        Assertions.assertThrows(PasswordInseguraException.class,
                ()->Usuario.instancia("Morena Gallardo", carrera,"more@gmail.com", password));
    }

}

