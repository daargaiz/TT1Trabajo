package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Entidad;
import com.ejemplo.proyecto.domain.EntidadMovil;
/**
 * Implementación base del comportamiento de una entidad móvil en el tablero.
 *
 * <p>Define cómo se mueve una entidad y su representación visual mediante color.
 * Las subclases deben sobreescribir los métodos para proporcionar comportamiento concreto.
 *
 * @see IEntidadMovilComportamiento
 * @see EntidadMovil
 */
public class EntidadMovilComportamiento implements IEntidadMovilComportamiento {
    /**
     * Mueve la entidad dentro de los límites del tablero.
     *
     * @param entidad          la entidad que se desea mover
     * @param dimensionTablero la longitud lado del tablero que delimita el movimiento
     * @return la entidad con su posición actualizada
     * @throws UnsupportedOperationException si la subclase no ha implementado este método
     */
    @Override
    public EntidadMovil moverse(Entidad entidad, int dimensionTablero) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
    /**
     * Devuelve el color asociado a esta entidad para su representación visual.
     *
     * @return cadena con el nombre o código del color (por ejemplo, {@code "RED"} o {@code "#FF0000"})
     * @throws UnsupportedOperationException si la subclase no ha implementado este método
     */
    @Override
    public String getColor() {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}
