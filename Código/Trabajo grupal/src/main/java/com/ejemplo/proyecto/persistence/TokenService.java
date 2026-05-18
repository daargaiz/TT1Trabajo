package com.ejemplo.proyecto.persistence;

/**
 * Servicio encargado de generar tokens de solicitud.
 */
public interface TokenService {
    /**
     * Genera un token para una nueva solicitud.
     * @return Token disponible.
     */
    int generarToken();
}
