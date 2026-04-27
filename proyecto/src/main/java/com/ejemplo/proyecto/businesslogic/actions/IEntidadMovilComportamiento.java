package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.businesslogic.printer.IEntidadColor;
/**
 * Comportamiento de una entidad con capacidad de desplazamiento en el tablero.
 *
 * <p>Combina el contrato de movimiento ({@link IEntidadComportamiento}) con el de
 * representación visual ({@link IEntidadColor}), de modo que toda entidad móvil
 * debe saber moverse y tiene un color asociado.
 */
public interface IEntidadMovilComportamiento extends IEntidadComportamiento, IEntidadColor {
}
