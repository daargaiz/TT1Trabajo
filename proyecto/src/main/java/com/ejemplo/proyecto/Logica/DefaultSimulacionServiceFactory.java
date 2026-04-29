package com.ejemplo.proyecto.Logica;

import com.ejemplo.proyecto.persistence.SimulacionService;
import com.ejemplo.proyecto.persistence.SimulacionServiceFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DefaultSimulacionServiceFactory implements SimulacionServiceFactory {
    @Override
    public SimulacionService crear(long semilla) {
        return new Service(new Random(semilla));
    }
}
