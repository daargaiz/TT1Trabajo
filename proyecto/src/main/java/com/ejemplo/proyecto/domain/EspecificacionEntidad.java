package com.ejemplo.proyecto.domain;

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
