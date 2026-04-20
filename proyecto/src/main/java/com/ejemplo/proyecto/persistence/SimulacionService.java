package com.ejemplo.proyecto.persistence;

import com.ejemplo.proyecto.domain.Simulacion;
import com.ejemplo.proyecto.domain.Tablero;

public interface SimulacionService {
    void nextStep(Tablero tablero);
    Simulacion ejecutarSimulacion(Tablero tablero, int steps);
}
