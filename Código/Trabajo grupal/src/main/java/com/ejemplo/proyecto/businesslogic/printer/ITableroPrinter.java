package com.ejemplo.proyecto.businesslogic.printer;

import com.ejemplo.proyecto.domain.Tablero;

/**
 * Contrato para imprimir información básica de un tablero.
 */
public interface ITableroPrinter {
    /**
     * Imprime la dimensión del tablero recibido.
     * @param t Tablero consultado.
     * @return Texto con la dimensión del tablero.
     */
    public String imprimirDimension(Tablero t);
}
