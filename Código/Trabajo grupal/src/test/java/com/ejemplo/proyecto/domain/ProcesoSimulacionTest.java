package com.ejemplo.proyecto.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProcesoSimulacionTest {

    @Test
    void arrancaEnEstadoPendiente() {
        ProcesoSimulacion proceso = new ProcesoSimulacion(solicitudValida());

        assertEquals(EstadoProcesoSimulacion.PENDIENTE, proceso.getEstado());
        assertFalse(proceso.isDone());
    }

    @Test
    void pasaACompletadoCuandoSeLeAsignaTableroYResultado() {
        ProcesoSimulacion proceso = new ProcesoSimulacion(solicitudValida());
        Tablero tablero = new Tablero(12);

        proceso.completar(tablero, "12\n0,0,0,red");

        assertTrue(proceso.isDone());
        assertEquals(tablero, proceso.getTablero());
        assertEquals("12\n0,0,0,red", proceso.getResultadoFormateado());
    }

    @Test
    void rechazaCompletarSinTablero() {
        ProcesoSimulacion proceso = new ProcesoSimulacion(solicitudValida());

        assertThrows(IllegalArgumentException.class, () -> proceso.completar(null, "12\n0,0,0,red"));
    }

    @Test
    void rechazaCompletarSinResultadoFormateado() {
        ProcesoSimulacion proceso = new ProcesoSimulacion(solicitudValida());

        assertThrows(IllegalArgumentException.class, () -> proceso.completar(new Tablero(12), " "));
    }

    private SolicitudSimulacion solicitudValida() {
        return new SolicitudSimulacion(
                1000,
                "dani",
                12,
                3,
                List.of(new EspecificacionEntidad(TipoEntidad.MOVIL, 1))
        );
    }
}
