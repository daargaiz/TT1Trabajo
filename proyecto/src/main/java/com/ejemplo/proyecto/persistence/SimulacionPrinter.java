package com.ejemplo.proyecto.persistence;

import com.ejemplo.proyecto.domain.Tablero;

/**
 * Puerto para convertir un tablero a texto.
 */
public interface SimulacionPrinter {
    /**
     * Genera la representación textual del tablero.
     * @param tablero Tablero a imprimir.
     * @return Texto resultante.
     */
    String print(Tablero tablero);
}
