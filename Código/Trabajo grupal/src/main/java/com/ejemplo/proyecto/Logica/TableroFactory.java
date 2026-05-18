package com.ejemplo.proyecto.Logica;

import com.ejemplo.proyecto.domain.Entidad;
import com.ejemplo.proyecto.domain.EspecificacionEntidad;
import com.ejemplo.proyecto.domain.Posicion;
import com.ejemplo.proyecto.domain.SolicitudSimulacion;
import com.ejemplo.proyecto.domain.Tablero;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Construye tableros iniciales colocando entidades en posiciones aleatorias reproducibles.
 */
@Component
public class TableroFactory {

    /**
     * Crea el tablero inicial para una solicitud concreta.
     * @param solicitud Solicitud con tamaño, entidades y cantidades.
     * @param semilla Semilla que determina el orden de colocación.
     * @return Tablero inicial con todas las entidades colocadas.
     */
    public Tablero crear(SolicitudSimulacion solicitud, long semilla) {
        if (solicitud.getTotalEntidadesSolicitadas() > solicitud.getLadoTablero() * solicitud.getLadoTablero()) {
            throw new IllegalArgumentException("No caben tantas entidades en el tablero solicitado");
        }

        Tablero tablero = new Tablero(solicitud.getLadoTablero());
        List<Posicion> posicionesDisponibles = crearPosicionesDisponibles(solicitud.getLadoTablero());
        Collections.shuffle(posicionesDisponibles, new Random(semilla));

        int indicePosicion = 0;
        for (EspecificacionEntidad especificacion : solicitud.getEspecificaciones()) {
            for (int i = 0; i < especificacion.cantidad(); i++) {
                Posicion posicion = posicionesDisponibles.get(indicePosicion++);
                Entidad entidad = especificacion.tipo().crearEntidad(posicion.x(), posicion.y());
                tablero.getEntidades().add(entidad);
            }
        }

        return tablero;
    }

    /**
     * Genera todas las posiciones disponibles del tablero antes de mezclarlas.
     */
    private List<Posicion> crearPosicionesDisponibles(int lado) {
        List<Posicion> posiciones = new ArrayList<>(lado * lado);
        for (int y = 0; y < lado; y++) {
            for (int x = 0; x < lado; x++) {
                posiciones.add(new Posicion(x, y));
            }
        }
        return posiciones;
    }
}
