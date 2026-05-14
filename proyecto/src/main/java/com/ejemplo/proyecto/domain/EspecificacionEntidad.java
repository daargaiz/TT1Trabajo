package com.ejemplo.proyecto.domain;

/**
 * Cantidad solicitada de un tipo concreto de entidad.
 * @param tipo Tipo de entidad que se debe crear.
 * @param cantidad Número de entidades de ese tipo.
 */
public record EspecificacionEntidad(TipoEntidad tipo, int cantidad) {
    public EspecificacionEntidad {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de entidad es obligatorio");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de entidades debe ser positiva");
        }
    }
}
