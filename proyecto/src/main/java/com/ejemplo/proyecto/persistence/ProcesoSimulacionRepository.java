package com.ejemplo.proyecto.persistence;

import com.ejemplo.proyecto.domain.ProcesoSimulacion;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de persistencia para procesos de simulación.
 */
public interface ProcesoSimulacionRepository {
    /**
     * Guarda o actualiza un proceso.
     * @param proceso Proceso que se quiere persistir.
     */
    void guardar(ProcesoSimulacion proceso);

    /**
     * Busca un proceso por usuario y token.
     * @param nombreUsuario Usuario propietario.
     * @param token Token de la solicitud.
     * @return Proceso si existe.
     */
    Optional<ProcesoSimulacion> buscarPorUsuarioYToken(String nombreUsuario, int token);

    /**
     * Busca los tokens asociados a un usuario.
     * @param nombreUsuario Usuario consultado.
     * @return Lista de tokens encontrados.
     */
    List<Integer> buscarTokensPorUsuario(String nombreUsuario);

    /**
     * Comprueba si un token ya está registrado.
     * @param token Token a validar.
     * @return true si existe.
     */
    boolean existeToken(int token);
}
