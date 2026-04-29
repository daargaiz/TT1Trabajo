package com.ejemplo.proyecto.persistence;

import com.ejemplo.proyecto.domain.ProcesoSimulacion;

import java.util.List;
import java.util.Optional;

public interface SolicitudSimulacionService {
    ProcesoSimulacion solicitar(
            String nombreUsuario,
            List<String> nombresEntidades,
            List<Integer> cantidadesIniciales
    );

    Optional<ProcesoSimulacion> consultar(String nombreUsuario, int token);

    List<Integer> listarTokensUsuario(String nombreUsuario);

    boolean comprobarSolicitud(String nombreUsuario, int token);
}
