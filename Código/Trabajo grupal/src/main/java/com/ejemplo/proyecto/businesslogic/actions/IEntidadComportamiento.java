package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Entidad;
/**
 * Contrato base para el comportamiento de movimiento de cualquier entidad en el tablero.
 *
 * <p>Todas las variantes de comportamiento (móvil, quieta, vírica) derivan o replican
 * este contrato, definiendo cómo una entidad reacciona al ser invocada en cada turno.
 *
 * @see IEntidadMovilComportamiento
 * @see IEntidadQuietaComportamiento
 * @see IEntidadViricaComportamiento
 */
public interface IEntidadComportamiento {
    /**
     * Mueve o actualiza la posición de la entidad dentro del tablero.
     *
     * @param entidad          la entidad sobre la que se aplica el comportamiento
     * @param dimensionTablero la longitud del lado del tablero cuadrado que delimita el movimiento
     * @return la entidad con su estado actualizado tras el movimiento
     */
    public Entidad moverse(Entidad entidad, int dimensionTablero);
}
