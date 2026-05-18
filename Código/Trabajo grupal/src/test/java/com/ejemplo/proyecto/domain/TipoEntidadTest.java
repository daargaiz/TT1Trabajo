package com.ejemplo.proyecto.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TipoEntidadTest {

    @Test
    void resuelveAliasDelTrabajoAnterior() {
        assertEquals(TipoEntidad.QUIETA, TipoEntidad.desdeNombre("Prueba1"));
        assertEquals(TipoEntidad.MOVIL, TipoEntidad.desdeNombre("prueba2"));
        assertEquals(TipoEntidad.VIRICA, TipoEntidad.desdeNombre("PRUEBA3"));
    }

    @Test
    void creaLaSubclaseEsperadaParaCadaTipo() {
        assertInstanceOf(EntidadQuieta.class, TipoEntidad.QUIETA.crearEntidad(1, 2));
        assertInstanceOf(EntidadMovil.class, TipoEntidad.MOVIL.crearEntidad(1, 2));
        assertInstanceOf(EntidadVirica.class, TipoEntidad.VIRICA.crearEntidad(1, 2));
    }

    @Test
    void rechazaNombresDesconocidos() {
        assertThrows(IllegalArgumentException.class, () -> TipoEntidad.desdeNombre("desconocida"));
    }
}
