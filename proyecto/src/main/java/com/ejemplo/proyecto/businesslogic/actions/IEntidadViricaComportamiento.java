package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.businesslogic.printer.IEntidadColor;
import com.ejemplo.proyecto.domain.Entidad;

import java.util.List;
/**
 * Comportamiento de una entidad con capacidad de propagación vírica en el tablero.
 *
 * <p>A diferencia de {@link IEntidadMovilComportamiento} e {@link IEntidadQuietaComportamiento},
 * esta interfaz <strong>no extiende</strong> {@link IEntidadComportamiento} porque su método
 * {@code moverse} tiene una firma distinta: devuelve {@code List<Entidad>} en lugar de
 * una sola {@code Entidad}, ya que una entidad vírica puede propagarse y generar
 * múltiples entidades en un mismo turno.
 *
 * @see IEntidadComportamiento
 */
public interface IEntidadViricaComportamiento extends IEntidadColor {
    /**
     * Propaga la entidad vírica por el tablero, pudiendo generar múltiples entidades resultado.
     *
     * <p>La lista devuelta puede contener la entidad original desplazada, nuevas entidades
     * generadas por contagio, o ambas. Una lista vacía indica que la entidad no ha
     * sobrevivido ni se ha propagado.
     *
     * @param entidad          la entidad vírica que se propaga
     * @param dimensionTablero el tamaño del tablero que delimita la propagación
     * @return lista de entidades resultantes de la propagación; nunca {@code null}
     */
    public List<Entidad> moverse(Entidad entidad, int dimensionTablero);
}
