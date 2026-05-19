package com.ejemplo.proyecto.persistence;

/**
 * Puerto para ejecutar solicitudes de simulación ya registradas.
 */
public interface SolicitudSimulacionProcessor {
    /**
     * Procesa una solicitud pendiente y guarda su resultado.
     * @param nombreUsuario Usuario propietario de la solicitud.
     * @param token Token de la solicitud.
     */
    void procesar(String nombreUsuario, int token);
}
