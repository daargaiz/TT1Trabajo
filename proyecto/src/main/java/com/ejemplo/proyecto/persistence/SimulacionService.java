package com.ejemplo.proyecto.persistence;

import com.ejemplo.proyecto.domain.Tablero;

public interface SimulacionService {
	void nextStep(Tablero tablero);
    void ejecutarSimulacion(Tablero tablero, int steps);
}
