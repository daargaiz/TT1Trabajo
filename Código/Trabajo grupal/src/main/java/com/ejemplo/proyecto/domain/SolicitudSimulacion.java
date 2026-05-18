package com.ejemplo.proyecto.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Recoge los datos necesarios para lanzar una simulación de un usuario.
 * Valida los valores básicos y conserva las especificaciones como lista inmutable.
 */
public class SolicitudSimulacion {
    private final int token;
    private final String nombreUsuario;
    private final int ladoTablero;
    private final int pasos;
    private final List<EspecificacionEntidad> especificaciones;

    /**
     * Construye una solicitud de simulación completa.
     * @param token Identificador generado para consultar la simulación.
     * @param nombreUsuario Usuario que realiza la solicitud.
     * @param ladoTablero Longitud del lado del tablero cuadrado.
     * @param pasos Número de pasos que se ejecutarán.
     * @param especificaciones Tipos de entidades y cantidades solicitadas.
     */
    public SolicitudSimulacion(
            int token,
            String nombreUsuario,
            int ladoTablero,
            int pasos,
            List<EspecificacionEntidad> especificaciones
    ) {
        if (token < 0) {
            throw new IllegalArgumentException("El token no puede ser negativo");
        }
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (ladoTablero <= 0) {
            throw new IllegalArgumentException("El lado del tablero debe ser positivo");
        }
        if (pasos < 0) {
            throw new IllegalArgumentException("El numero de pasos no puede ser negativo");
        }
        if (especificaciones == null || especificaciones.isEmpty()) {
            throw new IllegalArgumentException("La solicitud debe incluir al menos un tipo de entidad");
        }

        this.token = token;
        this.nombreUsuario = nombreUsuario.trim();
        this.ladoTablero = ladoTablero;
        this.pasos = pasos;
        this.especificaciones = Collections.unmodifiableList(new ArrayList<>(especificaciones));
    }

    public int getToken() {
        return token;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public int getLadoTablero() {
        return ladoTablero;
    }

    public int getPasos() {
        return pasos;
    }

    public List<EspecificacionEntidad> getEspecificaciones() {
        return especificaciones;
    }

    /**
     * Calcula el número total de entidades que deben colocarse en el tablero.
     * @return Suma de las cantidades de todas las especificaciones.
     */
    public int getTotalEntidadesSolicitadas() {
        return this.especificaciones.stream()
                .mapToInt(EspecificacionEntidad::cantidad)
                .sum();
    }
}
