package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Simulacion;
import com.ejemplo.proyecto.domain.Tablero;

public interface ISimulacionComportamiento {
    Simulacion ejecutarSimulacion(Tablero tablero, int steps);
}
