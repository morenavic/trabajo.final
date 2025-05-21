package usuario.models;

import com.backend.usuario.exceptions.DatosInvalidosException;
import com.backend.usuario.models.Carrera;
import com.backend.usuario.exceptions.DatosIncompletosException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullSource;

public class CarreraTest {

    @Test
    void instanciarCarrera_TodosLosAtributos_InstanciaCorrecta() {
        Carrera carrera = Carrera.instancia("Tecnicatura en Desarrollo Web");

        Assertions.assertEquals("Tecnicatura en Desarrollo Web", carrera.getNombreCarrera());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",  // Nombre vacío
            "  ",  // Nombre con solo espacios
    })
    @NullSource // Nombre nulo
    void instanciarCarrera_NombreIncompleto_DatosIncompletosException(String nombre) {
        Assertions.assertThrows(DatosIncompletosException.class,
                () -> Carrera.instancia(nombre));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Tecnicatura!", // Nombre con caracteres especiales
            "Tecnicatura123", // Nombre con números
    })
    void instanciarCarrera_NombreInvalido_DatosInvalidosException(String nombre) {
        Assertions.assertThrows(DatosInvalidosException.class,
                () -> Carrera.instancia(nombre));
    }
}

