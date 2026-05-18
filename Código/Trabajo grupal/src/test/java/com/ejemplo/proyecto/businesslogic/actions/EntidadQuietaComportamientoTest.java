package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Entidad;
import com.ejemplo.proyecto.domain.EntidadQuieta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntidadQuietaComportamientoTest {
    EntidadQuieta entidadQuieta;
    EntidadQuietaComportamiento entidadQuietaComportamiento;
    @BeforeEach
    void setUp() {
        entidadQuieta = new EntidadQuieta(1,1);
        entidadQuietaComportamiento = new EntidadQuietaComportamiento();
    }
    @AfterEach
    void tearDown() {
        entidadQuieta = null;
    }
    @Test
    void getEntidadQuietaComportamiento() {
        Entidad entidad = entidadQuietaComportamiento.moverse(entidadQuieta,5);
        assertEquals(1,entidad.getX(),"Se queda quieta");
        assertEquals(1,entidad.getY(),"Se queda quieta");
    }
    @Test
    void moverse_entidadNula_lanzaExcepcion() {
        assertThrows(NullPointerException.class,
                () -> entidadQuietaComportamiento.moverse(null, 5));
    }
    @Test
    void moverse_entidadEnEsquina_semantiene() {
        EntidadQuieta esquina = new EntidadQuieta(0, 0);
        Entidad result = entidadQuietaComportamiento.moverse(esquina, 5);
        assertEquals(0, result.getX());
        assertEquals(0, result.getY());
    }
    @Test
    void entidadEnElBorde_semantiene() {
        EntidadQuieta borde = new EntidadQuieta(4, 4);
        Entidad result = entidadQuietaComportamiento.moverse(borde, 5);
        assertEquals(4, result.getX());
        assertEquals(4, result.getY());
    }
    @Test
    void entidadEnLimiteExacto_esNula() {
        EntidadQuieta limite = new EntidadQuieta(5, 5);
        Entidad result = entidadQuietaComportamiento.moverse(limite, 5);
        assertNull(result);
    }
    @Test
    void entidadFueraDelTablero() {
        EntidadQuieta fuera = new EntidadQuieta(12,12);
        Entidad result = entidadQuietaComportamiento.moverse(fuera, 5);
        assertNull(result);
    }
    @Test
    void entidadConCoordendasNegativas_esNula() {
        EntidadQuieta negativa = new EntidadQuieta(-1, -1);
        Entidad result = entidadQuietaComportamiento.moverse(negativa, 5);
        assertNull(result);
    }
}
