package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Tablero;
/**
 * Contrato para la ejecución de una simulación completa sobre un tablero.
 *
 * <p>Una simulación consiste en aplicar repetidamente iteraciones sobre el tablero
 * durante un número determinado de pasos, recogiendo o transformando el resultado
 * en un objeto {@link Simulacion} que representa la evolución completa.
 *
 * @see ITableroComportamiento
 * @see Simulacion
 * @see Tablero
 */
public interface ISimulacionComportamiento {
    /**
     * Ejecuta la simulación sobre el tablero durante el número de pasos indicado.
     *
     * @param tablero el estado inicial del tablero sobre el que se simula
     * @param steps   el número de iteraciones a ejecutar
     * @return un objeto {@link Simulacion} con el resultado o historial de la simulación
     */
    Simulacion ejecutarSimulacion(Tablero tablero, int steps);
}
