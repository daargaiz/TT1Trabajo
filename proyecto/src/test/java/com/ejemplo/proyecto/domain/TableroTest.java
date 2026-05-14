package com.ejemplo.proyecto.domain;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    @Test
    @DisplayName("Dimensiones: ancho y altura deben coincidir con el lado")
    void testDimensiones() {
        Tablero t = new Tablero(10);
        assertAll(
            () -> assertEquals(10, t.getAncho()),
            () -> assertEquals(10, t.getAltura()),
            () -> assertEquals(10, t.getLado())
        );
    }

    @Test
    @DisplayName("estaDentro: validar correctamente los límites del tablero")
    void testLimites() {
        Tablero t = new Tablero(5);
        assertAll(
            () -> assertTrue(t.estaDentro(new Posicion(0, 0))),
            () -> assertTrue(t.estaDentro(new Posicion(4, 4))),
            () -> assertFalse(t.estaDentro(new Posicion(-1, 0))),
            () -> assertFalse(t.estaDentro(new Posicion(5, 5)))
        );
    }

    @Test
    @DisplayName("Historial: registrarInstantanea debe salvar el estado de las entidades")
    void testHistorial() {
        Tablero t = new Tablero(10);
        t.getEntidades().add(new EntidadMovil(2, 3));
        
        t.registrarInstantanea(1);
        
        assertEquals(1, t.getHistorial().size());
        assertEquals(1, t.getHistorial().get(0).tiempo());
        assertEquals("red", t.getHistorial().get(0).color());
    }
}