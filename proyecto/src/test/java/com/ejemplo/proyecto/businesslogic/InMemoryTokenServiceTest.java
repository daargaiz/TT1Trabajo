package com.ejemplo.proyecto.businesslogic;

import com.ejemplo.proyecto.Logica.InMemoryProcesoSimulacionRepository;
import com.ejemplo.proyecto.Logica.InMemoryTokenService;
import com.ejemplo.proyecto.domain.EspecificacionEntidad;
import com.ejemplo.proyecto.domain.ProcesoSimulacion;
import com.ejemplo.proyecto.domain.SolicitudSimulacion;
import com.ejemplo.proyecto.domain.TipoEntidad;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryTokenServiceTest {

    @Test
    void generaElPrimerTokenEsperadoSiNoHayNadaPersistido() {
        InMemoryTokenService tokenService = new InMemoryTokenService(new InMemoryProcesoSimulacionRepository());

        assertEquals(1000, tokenService.generarToken());
    }

    @Test
    void saltaLosTokensQueYaExistenEnElRepositorio() {
        InMemoryProcesoSimulacionRepository repository = new InMemoryProcesoSimulacionRepository();
        repository.guardar(new ProcesoSimulacion(new SolicitudSimulacion(
                1000,
                "dani",
                12,
                3,
                List.of(new EspecificacionEntidad(TipoEntidad.QUIETA, 1))
        )));

        InMemoryTokenService tokenService = new InMemoryTokenService(repository);

        assertEquals(1001, tokenService.generarToken());
    }

    @Test
    void generaTokensCrecientesEnLlamadasConsecutivas() {
        InMemoryTokenService tokenService = new InMemoryTokenService(new InMemoryProcesoSimulacionRepository());

        int primero = tokenService.generarToken();
        int segundo = tokenService.generarToken();

        assertTrue(segundo > primero);
    }
}
