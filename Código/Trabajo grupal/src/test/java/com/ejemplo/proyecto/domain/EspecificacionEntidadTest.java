package com.ejemplo.proyecto.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EspecificacionEntidadTest {

    @Test
    void rechazaTipoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new EspecificacionEntidad(null, 1));
    }

    @Test
    void rechazaCantidadNoPositiva() {
        assertThrows(IllegalArgumentException.class, () -> new EspecificacionEntidad(TipoEntidad.QUIETA, 0));
    }
}
