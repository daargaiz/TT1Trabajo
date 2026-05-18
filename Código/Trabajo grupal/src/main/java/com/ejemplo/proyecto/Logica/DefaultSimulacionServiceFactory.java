package com.ejemplo.proyecto.Logica;

import com.ejemplo.proyecto.persistence.SimulacionService;
import com.ejemplo.proyecto.persistence.SimulacionServiceFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Factoría por defecto de servicios de simulación.
 */
@Component
public class DefaultSimulacionServiceFactory implements SimulacionServiceFactory {
    /**
     * Crea un servicio de simulación con una semilla concreta.
     * @param semilla Semilla usada por el generador aleatorio.
     * @return Servicio listo para ejecutar simulaciones reproducibles.
     */
    @Override
    public SimulacionService crear(long semilla) {
        return new Service(new Random(semilla));
    }
}
