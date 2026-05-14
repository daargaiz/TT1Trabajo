package com.ejemplo.proyecto.persistence;

/**
 * Crea servicios de simulación configurados con una semilla.
 */
public interface SimulacionServiceFactory {
    /**
     * Crea un servicio preparado para una ejecución reproducible.
     * @param semilla Semilla del generador aleatorio.
     * @return Servicio de simulación.
     */
    SimulacionService crear(long semilla);
}
