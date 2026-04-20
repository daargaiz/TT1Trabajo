package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Tablero;

public class TableroComportamiento implements ITableroComportamiento{private final IEntidadMovilComportamiento movilComportamiento;
    private final IEntidadQuietaComportamiento quietaComportamiento;
    private final IEntidadViricaComportamiento viricaComportamiento;

    public TableroComportamiento(
            IEntidadMovilComportamiento movilComportamiento,
            IEntidadQuietaComportamiento quietaComportamiento,
            IEntidadViricaComportamiento viricaComportamiento
    ) {
        this.movilComportamiento = movilComportamiento;
        this.quietaComportamiento = quietaComportamiento;
        this.viricaComportamiento = viricaComportamiento;
    }

    @Override
    public Tablero iteracion(Tablero tablero) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}
