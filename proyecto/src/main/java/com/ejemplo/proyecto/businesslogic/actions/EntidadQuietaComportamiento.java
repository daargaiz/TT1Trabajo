package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Entidad;

public class EntidadQuietaComportamiento implements IEntidadQuietaComportamiento {
    @Override
    public Entidad moverse(Entidad entidad, int dimensionTablero) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    @Override
    public String getColor() {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}
