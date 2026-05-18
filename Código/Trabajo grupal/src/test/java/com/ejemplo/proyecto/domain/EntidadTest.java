package com.ejemplo.proyecto.domain;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class EntidadTest {

    @Test
    @DisplayName("moverA: actualiza correctamente las coordenadas x e y")
    void testMoverA() {
        Entidad e = new EntidadMovil(0, 0);
        e.moverA(new Posicion(5, 7));
        
        assertAll(
            () -> assertEquals(5, e.getX()),
            () -> assertEquals(7, e.getY()),
            () -> assertEquals(new Posicion(5, 7), e.getPosicion())
        );
    }

    @Test
    @DisplayName("equals/hashCode: comparación por posición y tipo")
    void testEquals() {
        Entidad e1 = new EntidadMovil(1, 1);
        Entidad e2 = new EntidadMovil(1, 1);
        Entidad e3 = new EntidadQuieta(1, 1); // Diferente clase

        assertAll(
            () -> assertEquals(e1, e2, "Mismo tipo y posición deben ser iguales"),
            () -> assertNotEquals(e1, e3, "Diferente tipo no debe ser igual aunque coincida la posición"),
            () -> assertEquals(e1.hashCode(), e2.hashCode())
        );
    }
}