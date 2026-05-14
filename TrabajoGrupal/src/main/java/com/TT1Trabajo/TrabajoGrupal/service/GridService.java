package com.TT1Trabajo.TrabajoGrupal.service;

/**
 * Servicio encargado de proporcionar datos del grid.
 */
public interface GridService {
    /**
     * Recupera o genera los datos del grid para un token.
     * @param token Token de consulta.
     * @return Datos del grid en formato texto.
     */
    String getGridData(String token);
}
