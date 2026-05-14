package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.Logica.Service;
import com.ejemplo.proyecto.domain.Simulacion;
import com.ejemplo.proyecto.domain.Tablero;

/**
 * Adaptador de simulación para la API businesslogic.
 * Delega la ejecución real en el servicio principal de lógica.
 */
public class SimulacionComportamiento implements ISimulacionComportamiento {
    private final Service delegate;

    /**
     * Crea el adaptador con el servicio de simulación por defecto.
     */
    public SimulacionComportamiento() {
        this.delegate = new Service();
    }

    /**
     * Avanza un paso en el tablero recibido.
     * @param tablero Tablero que se actualiza.
     */
    @Override
    public void nextStep(Tablero tablero) {
        this.delegate.nextStep(tablero);
    }

    /**
     * Ejecuta varios pasos de simulación.
     * @param tablero Tablero inicial.
     * @param steps Número de pasos a ejecutar.
     * @return Simulación resultante.
     */
    @Override
    public Simulacion ejecutarSimulacion(Tablero tablero, int steps) {
        return this.delegate.ejecutarSimulacion(tablero, steps);
    }
}
