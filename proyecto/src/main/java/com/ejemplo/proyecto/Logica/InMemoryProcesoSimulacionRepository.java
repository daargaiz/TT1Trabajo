package com.ejemplo.proyecto.Logica;

import com.ejemplo.proyecto.domain.ProcesoSimulacion;
import com.ejemplo.proyecto.persistence.ProcesoSimulacionRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repositorio en memoria para procesos de simulación.
 * Usa usuario y token como clave lógica de consulta.
 */
@Repository
public class InMemoryProcesoSimulacionRepository implements ProcesoSimulacionRepository {
    private final Map<String, ProcesoSimulacion> procesos = new ConcurrentHashMap<>();
    private final Map<Integer, String> tokens = new ConcurrentHashMap<>();

    @Override
    public void guardar(ProcesoSimulacion proceso) {
        String clave = clave(
                proceso.getSolicitud().getNombreUsuario(),
                proceso.getSolicitud().getToken()
        );
        this.procesos.put(clave, proceso);
        this.tokens.put(proceso.getSolicitud().getToken(), clave);
    }

    @Override
    public Optional<ProcesoSimulacion> buscarPorUsuarioYToken(String nombreUsuario, int token) {
        return Optional.ofNullable(this.procesos.get(clave(nombreUsuario, token)));
    }

    @Override
    public List<Integer> buscarTokensPorUsuario(String nombreUsuario) {
        String usuarioNormalizado = normalizarUsuario(nombreUsuario);
        return this.procesos.values().stream()
                .filter(proceso -> normalizarUsuario(proceso.getSolicitud().getNombreUsuario()).equals(usuarioNormalizado))
                .map(proceso -> proceso.getSolicitud().getToken())
                .distinct()
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    @Override
    public boolean existeToken(int token) {
        return this.tokens.containsKey(token);
    }

    private String clave(String nombreUsuario, int token) {
        return normalizarUsuario(nombreUsuario) + ":" + token;
    }

    private String normalizarUsuario(String nombreUsuario) {
        return nombreUsuario.trim().toLowerCase();
    }
}
