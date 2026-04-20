package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Entidad;
import com.ejemplo.proyecto.domain.EntidadVirica;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntidadViricaComportamientoTest{
    EntidadVirica entidadVirica;
    EntidadViricaComportamiento entidadViricaComportamiento;
    @BeforeEach
    void setUp() {
        entidadVirica = new EntidadVirica(1,1);
        entidadViricaComportamiento = new EntidadViricaComportamiento();
    }
    @AfterEach
    void tearDown() {
        entidadVirica = null;
        entidadViricaComportamiento = null;
    }
    @Test
    void unaSeQuedaQuietaComportamiento() {
        List<Entidad> entidadV = entidadViricaComportamiento.moverse(entidadVirica,5);
        assertTrue(entidadV.contains(entidadVirica));
    }
    @Test
    void moverse_devuelveDosEntidades() {
        List<Entidad> result = entidadViricaComportamiento.moverse(entidadVirica, 5);
        assertEquals(2, result.size());
    }
    @Test
    void moverse_copiaEsAdyacente() {
        List<Entidad> result = entidadViricaComportamiento.moverse(entidadVirica, 5);
        Entidad copia = result.stream()
                .filter(e -> !e.equals(entidadVirica))
                .findFirst().orElseThrow();
        int dx = Math.abs(copia.getX() - entidadVirica.getX());
        int dy = Math.abs(copia.getY() - entidadVirica.getY());
        assertTrue((dx == 1 && dy == 0) || (dx == 0 && dy == 1));
    }
    @Test
    void moverse_entidadNula_lanzaExcepcion() {
        assertThrows(NullPointerException.class,
                () -> entidadViricaComportamiento.moverse(null, 5));
    }
    @Test
    void moverse_entidadFuera_esNula() {
        EntidadVirica fuera = new EntidadVirica(10, 10);
        assertNull(entidadViricaComportamiento.moverse(fuera, 5));
    }
    @Test
    void moverse_desdeEsquina_copiaEnTablero() {
        EntidadVirica esquina = new EntidadVirica(0, 0);
        List<Entidad> result = entidadViricaComportamiento.moverse(esquina, 5);
        Entidad copia = result.stream()
                .filter(e -> !e.equals(esquina))
                .findFirst().orElseThrow();
        assertTrue(copia.getX() >= 0 && copia.getY() >= 0);
        assertTrue(copia.getX() < 5 && copia.getY() < 5);
    }
}
