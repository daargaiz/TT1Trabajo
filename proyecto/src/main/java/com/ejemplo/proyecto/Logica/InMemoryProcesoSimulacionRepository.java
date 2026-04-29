package com.ejemplo.proyecto.Logica;

import com.ejemplo.proyecto.domain.ProcesoSimulacion;
import com.ejemplo.proyecto.persistence.ProcesoSimulacionRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
    public boolean existeToken(int token) {
        return this.tokens.containsKey(token);
    }

    private String clave(String nombreUsuario, int token) {
        return nombreUsuario.trim().toLowerCase() + ":" + token;
    }
}
