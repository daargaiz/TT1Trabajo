package com.ejemplo.proyecto.persistence;

import com.ejemplo.proyecto.domain.ProcesoSimulacion;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación para crear y consultar solicitudes de simulación.
 */
public interface SolicitudSimulacionService {
    /**
     * Registra y ejecuta una nueva solicitud para un usuario.
     * @param nombreUsuario Usuario que solicita la simulación.
     * @param nombresEntidades Nombres de los tipos de entidad.
     * @param cantidadesIniciales Cantidades asociadas a cada tipo.
     * @return Proceso de simulación creado.
     */
    ProcesoSimulacion solicitar(
            String nombreUsuario,
            List<String> nombresEntidades,
            List<Integer> cantidadesIniciales
    );

    /**
     * Consulta una solicitud concreta de un usuario.
     * @param nombreUsuario Usuario propietario de la solicitud.
     * @param token Token de la solicitud.
     * @return Proceso si existe.
     */
    Optional<ProcesoSimulacion> consultar(String nombreUsuario, int token);

    /**
     * Lista los tokens registrados para un usuario.
     * @param nombreUsuario Usuario consultado.
     * @return Tokens asociados al usuario.
     */
    List<Integer> listarTokensUsuario(String nombreUsuario);

    /**
     * Comprueba si una solicitud existe y está completada.
     * @param nombreUsuario Usuario propietario de la solicitud.
     * @param token Token de la solicitud.
     * @return true si la solicitud está completada.
     */
    boolean comprobarSolicitud(String nombreUsuario, int token);
}
