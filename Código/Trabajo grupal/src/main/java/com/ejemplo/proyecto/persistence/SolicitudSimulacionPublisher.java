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

    /**
     * Indica si el procesamiento queda delegado a un consumidor externo.
     * @return true si el publicador activa un flujo asíncrono real.
     */
    default boolean isAsincrono() {
        return false;
    }
}
