package com.ejemplo.proyecto.domain;

/**
 * Instantánea serializable de una entidad en un tiempo concreto de la simulación.
 * @param tiempo Instante de la simulación.
 * @param x Coordenada horizontal.
 * @param y Coordenada vertical.
 * @param color Color con el que se representa la entidad.
 */
public record EstadoEntidad(int tiempo, int x, int y, String color) {
}
