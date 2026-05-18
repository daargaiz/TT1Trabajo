package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Tablero;
/**
 * Contrato para aplicar una única iteración sobre el estado del tablero.
 *
 * <p>Representa un paso discreto de la simulación: dado un tablero en un instante,
 * produce el tablero resultante tras aplicar las reglas de comportamiento de todas
 * sus entidades. Es el núcleo que {@link ISimulacionComportamiento} invoca repetidamente.
 *
 * @see ISimulacionComportamiento
 * @see Tablero
 */
public interface ITableroComportamiento {
    /**
     * Calcula el estado del tablero tras aplicar una iteración de la simulación.
     *
     * @param tablero el estado actual del tablero
     * @return el nuevo estado del tablero tras la iteración; nunca {@code null}
     */
    public Tablero iteracion(Tablero tablero);
}
