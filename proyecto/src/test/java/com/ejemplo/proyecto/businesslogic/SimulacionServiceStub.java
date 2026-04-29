package com.ejemplo.proyecto.businesslogic;

import com.ejemplo.proyecto.Logica.Service;
import com.ejemplo.proyecto.businesslogic.actions.ISimulacionComportamiento;
import com.ejemplo.proyecto.domain.Simulacion;
import com.ejemplo.proyecto.domain.Tablero;

public class SimulacionServiceStub implements ISimulacionComportamiento {
    private final Service delegate = new Service();

    @Override
    public void nextStep(Tablero tablero) {
        this.delegate.nextStep(tablero);
    }

    @Override
    public Simulacion ejecutarSimulacion(Tablero tablero, int steps) {
        return this.delegate.ejecutarSimulacion(tablero, steps);
    }
}
