package com.ejemplo.proyecto.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SolicitudSimulacionTest {

    @Test
    void calculaElTotalDeEntidadesSolicitadas() {
        SolicitudSimulacion solicitud = new SolicitudSimulacion(
                1000,
                "dani",
                12,
                3,
                List.of(
                        new EspecificacionEntidad(TipoEntidad.QUIETA, 1),
                        new EspecificacionEntidad(TipoEntidad.MOVIL, 2)
                )
        );

        assertEquals(3, solicitud.getTotalEntidadesSolicitadas());
    }

    @Test
    void rechazaTokenNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new SolicitudSimulacion(
                -1,
                "dani",
                12,
                3,
                List.of(new EspecificacionEntidad(TipoEntidad.QUIETA, 1))
        ));
    }

    @Test
    void rechazaUsuarioVacio() {
        assertThrows(IllegalArgumentException.class, () -> new SolicitudSimulacion(
                1,
                " ",
                12,
                3,
                List.of(new EspecificacionEntidad(TipoEntidad.QUIETA, 1))
        ));
    }

    @Test
    void rechazaLadoNoPositivo() {
        assertThrows(IllegalArgumentException.class, () -> new SolicitudSimulacion(
                1,
                "dani",
                0,
                3,
                List.of(new EspecificacionEntidad(TipoEntidad.QUIETA, 1))
        ));
    }

    @Test
    void rechazaPasosNegativos() {
        assertThrows(IllegalArgumentException.class, () -> new SolicitudSimulacion(
                1,
                "dani",
                12,
                -1,
                List.of(new EspecificacionEntidad(TipoEntidad.QUIETA, 1))
        ));
    }

    @Test
    void rechazaListaVaciaDeEspecificaciones() {
        assertThrows(IllegalArgumentException.class, () -> new SolicitudSimulacion(
                1,
                "dani",
                12,
                3,
                List.of()
        ));
    }
}
