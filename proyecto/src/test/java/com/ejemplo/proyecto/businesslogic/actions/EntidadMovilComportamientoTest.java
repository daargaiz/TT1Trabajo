package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.EntidadMovil;
import com.ejemplo.proyecto.domain.EntidadQuieta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntidadMovilComportamientoTest {
    EntidadMovil entidadMovil;
    EntidadMovilComportamiento entidadMovilComportamiento;
    @BeforeEach
    void setUp() {
        entidadMovil = new EntidadMovil(1,1);
        entidadMovilComportamiento = new EntidadMovilComportamiento();
    }
    @AfterEach
    void tearDown() {
        entidadMovil = null;
        entidadMovilComportamiento = null;
    }
    @Test
    void moverse_resultadoEsAdyacente() {
        EntidadMovil result = entidadMovilComportamiento.moverse(entidadMovil, 12);
        int dx = Math.abs(result.getX() - entidadMovil.getX());
        int dy = Math.abs(result.getY() - entidadMovil.getY());
        assertTrue((dx == 1 && dy == 0) || (dx == 0 && dy == 1));
    }
    @Test
    void moverse_entidadNula_lanzaExcepcion() {
        assertThrows(NullPointerException.class,
                () -> entidadMovilComportamiento.moverse(null, 12));
    }
    @Test
    void moverse_entidadFuera_esNula() {
        EntidadMovil fuera = new EntidadMovil(15, 15);
        assertNull(entidadMovilComportamiento.moverse(fuera, 12));
    }
    @Test
    void moverse_desdeEsquina_resultadoDentroDelTablero() {
        EntidadMovil esquina = new EntidadMovil(0, 0);
        EntidadMovil result = entidadMovilComportamiento.moverse(esquina, 12);
        assertTrue(result.getX() >= 0 && result.getY() >= 0);
    }
    @Test
    void moverse_desdeBordeLateral_noCaeAlVacio() {
        EntidadMovil borde = new EntidadMovil(0, 5);
        EntidadMovil result = entidadMovilComportamiento.moverse(borde, 12);
        assertTrue(result.getX() >= 0);
    }
    @Test
    void moverse_enLimiteExacto_esNula() {
        EntidadMovil limite = new EntidadMovil(12, 12);
        assertNull(entidadMovilComportamiento.moverse(limite, 12));
    }
    @Test
    void moverse_coordenadasNegativas_esNula() {
        EntidadMovil negativa = new EntidadMovil(-1, -1);
        assertNull(entidadMovilComportamiento.moverse(negativa, 12));
    }
}
