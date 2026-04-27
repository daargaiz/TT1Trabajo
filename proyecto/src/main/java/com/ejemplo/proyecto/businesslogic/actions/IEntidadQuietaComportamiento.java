package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.businesslogic.printer.IEntidadColor;
/**
 * Comportamiento de una entidad estática que no se desplaza en el tablero.
 *
 * <p>Aunque hereda el contrato de movimiento de {@link IEntidadComportamiento},
 * las implementaciones deben devolver la entidad sin modificar su posición.
 * También hereda {@link IEntidadColor} para su representación visual.
 */
public interface IEntidadQuietaComportamiento extends IEntidadComportamiento, IEntidadColor {
}
