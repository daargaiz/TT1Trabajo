package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.Logica.Service;
import com.ejemplo.proyecto.domain.Tablero;

public class TableroComportamiento implements ITableroComportamiento {
    private final IEntidadMovilComportamiento movilComportamiento;
    private final IEntidadQuietaComportamiento quietaComportamiento;
    private final IEntidadViricaComportamiento viricaComportamiento;
    private final Service delegate;

    public TableroComportamiento(
            IEntidadMovilComportamiento movilComportamiento,
            IEntidadQuietaComportamiento quietaComportamiento,
            IEntidadViricaComportamiento viricaComportamiento
    ) {
        this.movilComportamiento = movilComportamiento;
        this.quietaComportamiento = quietaComportamiento;
        this.viricaComportamiento = viricaComportamiento;
        this.delegate = new Service();
    }

    @Override
    public Tablero iteracion(Tablero tablero) {
        if (tablero == null) {
            throw new NullPointerException("El tablero no puede ser nulo");
        }

        // La iteración efectiva del dominio ya vive en la capa Logica.
        // Esta clase actúa como adaptador para la API businesslogic.
        this.delegate.nextStep(tablero);
        return tablero;
    }
}
