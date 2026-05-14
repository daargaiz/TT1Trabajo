package com.ejemplo.proyecto.businesslogic;

import com.ejemplo.proyecto.Logica.TableroFactory;
import com.ejemplo.proyecto.domain.EspecificacionEntidad;
import com.ejemplo.proyecto.domain.SolicitudSimulacion;
import com.ejemplo.proyecto.domain.Tablero;
import com.ejemplo.proyecto.domain.TipoEntidad;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TableroFactoryTest {
    private final TableroFactory factory = new TableroFactory();

    @Test
    void creaElNumeroSolicitadoDeEntidadesSinSolaparlas() {
        SolicitudSimulacion solicitud = new SolicitudSimulacion(
                7,
                "dani",
                4,
                0,
                List.of(
                        new EspecificacionEntidad(TipoEntidad.QUIETA, 1),
                        new EspecificacionEntidad(TipoEntidad.MOVIL, 2),
                        new EspecificacionEntidad(TipoEntidad.VIRICA, 1)
                )
        );

        Tablero tablero = factory.crear(solicitud, 99L);

        assertEquals(4, tablero.getEntidades().size());
        assertEquals(4, tablero.getEntidades().stream()
                .map(entidad -> entidad.getX() + ":" + entidad.getY())
                .distinct()
                .count());
    }

    @Test
    void rechazaSolicitudesQueNoCabenEnElTablero() {
        SolicitudSimulacion solicitud = new SolicitudSimulacion(
                8,
                "dani",
                2,
                0,
                List.of(new EspecificacionEntidad(TipoEntidad.MOVIL, 5))
        );

        assertThrows(IllegalArgumentException.class, () -> factory.crear(solicitud, 1L));
    }
    
    @Test
    @DisplayName("crear: las entidades generadas están siempre dentro de los límites")
    void testEntidadesDentroDeLimites() {
        SolicitudSimulacion solicitud = new SolicitudSimulacion(100, "dani", 5, 0, 
            List.of(new EspecificacionEntidad(TipoEntidad.MOVIL, 10)));
        
        Tablero tablero = factory.crear(solicitud, 1L);
        
        assertTrue(tablero.getEntidades().stream()
            .allMatch(e -> e.getX() >= 0 && e.getX() < 5 && e.getY() >= 0 && e.getY() < 5));
    }
}
