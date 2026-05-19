package com.ejemplo.proyecto.persistence;

/**
 * Puerto para publicar solicitudes de simulación hacia un sistema de mensajería.
 */
public interface SolicitudSimulacionPublisher {
    /**
     * Publica una solicitud registrada para que pueda procesarse de forma desacoplada.
     * @param nombreUsuario Usuario propietario de la solicitud.
     * @param token Token de la solicitud.
     */
    void publicar(String nombreUsuario, int token);
}
