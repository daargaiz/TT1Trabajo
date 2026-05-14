package com.ejemplo.proyecto.persistence;

import com.ejemplo.proyecto.domain.Tablero;

/**
 * Servicio para generar datos de grid en texto o como tablero.
 */
public interface GridDataService {
    /**
     * Genera la representación textual con pasos por defecto.
     * @param token Token usado como semilla.
     * @return Texto del tablero generado.
     */
    String generarTexto(String token);

    /**
     * Genera la representación textual con pasos concretos.
     * @param token Token usado como semilla.
     * @param pasos Número de pasos a ejecutar.
     * @return Texto del tablero generado.
     */
    String generarTexto(String token, int pasos);

    /**
     * Genera un tablero con pasos por defecto.
     * @param token Token usado como semilla.
     * @return Tablero generado.
     */
    Tablero generarTablero(String token);

    /**
     * Genera un tablero con pasos concretos.
     * @param token Token usado como semilla.
     * @param pasos Número de pasos a ejecutar.
     * @return Tablero generado.
     */
    Tablero generarTablero(String token, int pasos);
}
