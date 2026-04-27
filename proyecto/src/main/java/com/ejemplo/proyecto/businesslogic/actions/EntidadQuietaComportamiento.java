package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Entidad;
/**
 * Implementación del comportamiento de una entidad estática en el tablero.
 *
 * <p>A diferencia de {@link EntidadMovilComportamiento}, esta clase representa
 * entidades que no se desplazan. El método {@code moverse} puede devolver la
 * entidad sin modificar su posición.
 *
 * @see IEntidadQuietaComportamiento
 * @see Entidad
 */
public class EntidadQuietaComportamiento implements IEntidadQuietaComportamiento {
    /**
     * Operación de movimiento para una entidad estática.
     *
     * <p>Al ser una entidad quieta, se espera que la implementación devuelva
     * la entidad en la misma posición, ignorando {@code dimensionTablero}.
     *
     * @param entidad          la entidad sobre la que se invoca el comportamiento
     * @param dimensionTablero el tamaño del tablero (no utilizado en entidades estáticas)
     * @return la entidad sin cambios en su posición
     * @throws UnsupportedOperationException si la subclase no ha implementado este método
     */
    @Override
    public Entidad moverse(Entidad entidad, int dimensionTablero) {
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
