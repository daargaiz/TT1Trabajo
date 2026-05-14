package com.ejemplo.proyecto.businesslogic.printer;

import com.ejemplo.proyecto.domain.Tablero;

/**
 * Contrato para imprimir una simulación a partir de su tablero.
 */
public interface SimulacionPrinter {
	/**
	 * Genera la representación textual del tablero.
	 * @param tablero Tablero que se quiere imprimir.
	 * @return Texto resultante.
	 */
	String print(Tablero tablero);
}
