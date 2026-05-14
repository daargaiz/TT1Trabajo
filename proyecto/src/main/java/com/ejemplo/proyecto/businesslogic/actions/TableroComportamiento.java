package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.Logica.Service;
import com.ejemplo.proyecto.domain.Tablero;

/**
 * Adaptador de comportamiento de tablero que delega la iteración en la lógica principal.
 */
public class TableroComportamiento implements ITableroComportamiento {
    private final IEntidadMovilComportamiento movilComportamiento;
    private final IEntidadQuietaComportamiento quietaComportamiento;
    private final IEntidadViricaComportamiento viricaComportamiento;
    private final Service delegate;

    /**
     * Construye el adaptador manteniendo las dependencias de comportamiento esperadas por la API.
     * @param movilComportamiento Comportamiento de entidades móviles.
     * @param quietaComportamiento Comportamiento de entidades quietas.
     * @param viricaComportamiento Comportamiento de entidades víricas.
     */
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

    /**
     * Ejecuta una iteración del tablero recibido.
     * @param tablero Tablero que se va a actualizar.
     * @return El mismo tablero con el siguiente estado aplicado.
     */
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
