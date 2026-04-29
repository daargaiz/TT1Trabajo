package com.ejemplo.proyecto.businesslogic;

import com.ejemplo.proyecto.Logica.InMemoryProcesoSimulacionRepository;
import com.ejemplo.proyecto.domain.EspecificacionEntidad;
import com.ejemplo.proyecto.domain.ProcesoSimulacion;
import com.ejemplo.proyecto.domain.SolicitudSimulacion;
import com.ejemplo.proyecto.domain.Tablero;
import com.ejemplo.proyecto.domain.TipoEntidad;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryProcesoSimulacionRepositoryTest {

    @Test
    void guardaYRecuperaProcesosPorUsuarioYTokenSinSensibilidadAMayusculas() {
        InMemoryProcesoSimulacionRepository repository = new InMemoryProcesoSimulacionRepository();
        ProcesoSimulacion proceso = crearProceso(1000, "Dani");

        repository.guardar(proceso);

        ProcesoSimulacion recuperado = repository.buscarPorUsuarioYToken("dani", 1000).orElseThrow();

        assertEquals(1000, recuperado.getSolicitud().getToken());
        assertEquals("Dani", recuperado.getSolicitud().getNombreUsuario());
    }

    @Test
    void informaSiUnTokenExisteOTodaviaNo() {
        InMemoryProcesoSimulacionRepository repository = new InMemoryProcesoSimulacionRepository();

        assertFalse(repository.existeToken(1000));

        repository.guardar(crearProceso(1000, "dani"));

        assertTrue(repository.existeToken(1000));
    }

    @Test
    void sobrescribeElProcesoCuandoSeGuardaDeNuevoLaMismaClave() {
        InMemoryProcesoSimulacionRepository repository = new InMemoryProcesoSimulacionRepository();
        ProcesoSimulacion proceso = crearProceso(1000, "dani");
        repository.guardar(proceso);

        Tablero tablero = new Tablero(12);
        proceso.completar(tablero, "12\n0,0,0,red");
        repository.guardar(proceso);

        ProcesoSimulacion recuperado = repository.buscarPorUsuarioYToken("dani", 1000).orElseThrow();

        assertTrue(recuperado.isDone());
        assertEquals("12\n0,0,0,red", recuperado.getResultadoFormateado());
    }

    private ProcesoSimulacion crearProceso(int token, String usuario) {
        SolicitudSimulacion solicitud = new SolicitudSimulacion(
                token,
                usuario,
                12,
                3,
                List.of(new EspecificacionEntidad(TipoEntidad.QUIETA, 1))
        );
        return new ProcesoSimulacion(solicitud);
    }
}
