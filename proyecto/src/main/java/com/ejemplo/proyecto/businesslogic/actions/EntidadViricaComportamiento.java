package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Entidad;

import java.util.List;
/**
 * Implementación del comportamiento de una entidad vírica en el tablero.
 *
 * <p>A diferencia de {@link EntidadMovilComportamiento} y {@link EntidadQuietaComportamiento},
 * esta clase representa entidades con capacidad de propagación: al moverse pueden
 * generar múltiples entidades nuevas, modelando un comportamiento de tipo vírico o expansivo.
 *
 * @see IEntidadViricaComportamiento
 * @see Entidad
 */
public class EntidadViricaComportamiento implements IEntidadViricaComportamiento {
    /**
     * Propaga la entidad vírica por el tablero, pudiendo generar múltiples entidades resultado.
     *
     * <p>A diferencia del movimiento convencional, este método devuelve una lista que puede
     * contener la entidad original desplazada, nuevas entidades generadas por propagación,
     * o ambas. La lista vacía indicaría que la entidad no ha sobrevivido ni se ha propagado.
     *
     * @param entidad          la entidad vírica que se propaga
     * @param dimensionTablero el tamaño del tablero que delimita la propagación
     * @return lista de entidades resultantes de la propagación; nunca {@code null}
     * @throws UnsupportedOperationException si la subclase no ha implementado este método
     */
    @Override
    public List<Entidad> moverse(Entidad entidad, int dimensionTablero) {
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
