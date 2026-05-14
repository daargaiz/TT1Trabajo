package com.ejemplo.proyecto.persistence;

import com.ejemplo.proyecto.domain.Simulacion;
import com.ejemplo.proyecto.domain.Tablero;

/**
 * Puerto de lógica para avanzar simulaciones.
 */
public interface SimulacionService {
    /**
     * Avanza un paso de simulación.
     * @param tablero Tablero que se actualiza.
     */
    void nextStep(Tablero tablero);

    /**
     * Ejecuta varios pasos de simulación.
     * @param tablero Tablero inicial.
     * @param steps Número de pasos a ejecutar.
     * @return Simulación resultante.
     */
    Simulacion ejecutarSimulacion(Tablero tablero, int steps);
}
