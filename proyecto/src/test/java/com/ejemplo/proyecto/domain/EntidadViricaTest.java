package com.ejemplo.proyecto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EntidadViricaTest {

    @Test
    @DisplayName("Constructor: debe usar la probabilidad por defecto si no se indica")
    void testConstructorPorDefecto() {
        EntidadVirica v = new EntidadVirica(1, 1);
        assertEquals(0.25d, v.getProbabilidadExpansion(), "Debe usar 0.25 por defecto");
    }

    @Test
    @DisplayName("setProbabilidadExpansion: debe aceptar valores válidos en [0, 1]")
    void testProbabilidadValida() {
        EntidadVirica v = new EntidadVirica(0, 0);
        assertDoesNotThrow(() -> v.setProbabilidadExpansion(0.5));
        assertEquals(0.5, v.getProbabilidadExpansion());
    }

    @Test
    @DisplayName("setProbabilidadExpansion: debe lanzar excepción si el valor es < 0 o > 1")
    void testProbabilidadInvalida() {
        EntidadVirica v = new EntidadVirica(0, 0);
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> v.setProbabilidadExpansion(-0.1), "No debe aceptar negativos"),
            () -> assertThrows(IllegalArgumentException.class, () -> v.setProbabilidadExpansion(1.01), "No debe aceptar mayores a 1")
        );
    }

    @Test
    @DisplayName("copiarEn: debe crear una copia con la misma probabilidad en otra posición")
    void testCopiarEn() {
        EntidadVirica original = new EntidadVirica(2, 2, 0.8);
        EntidadVirica copia = original.copiarEn(3, 3);
        
        assertAll(
            () -> assertEquals(3, copia.getX()),
            () -> assertEquals(3, copia.getY()),
            () -> assertEquals(0.8, copia.getProbabilidadExpansion(), "La copia debe mantener la probabilidad"),
            () -> assertNotSame(original, copia, "Deben ser objetos distintos")
        );
    }

    @Test
    @DisplayName("getTipo y getColor: deben devolver los valores específicos")
    void testValoresVisuales() {
        EntidadVirica v = new EntidadVirica(0, 0);
        assertEquals("virica", v.getTipo());
        assertEquals("green", v.getColor());
    }
}