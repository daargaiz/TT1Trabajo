package com.ejemplo.proyecto.persistence;

import com.ejemplo.proyecto.domain.ProcesoSimulacion;

import java.util.List;
import java.util.Optional;

public interface ProcesoSimulacionRepository {
    void guardar(ProcesoSimulacion proceso);

    Optional<ProcesoSimulacion> buscarPorUsuarioYToken(String nombreUsuario, int token);

    List<Integer> buscarTokensPorUsuario(String nombreUsuario);

    boolean existeToken(int token);
}
