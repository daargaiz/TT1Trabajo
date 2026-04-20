package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Entidad;

import java.util.List;

public class EntidadViricaComportamiento implements IEntidadViricaComportamiento {

    @Override
    public List<Entidad> moverse(Entidad entidad, int dimensionTablero) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    @Override
    public String getColor() {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}
