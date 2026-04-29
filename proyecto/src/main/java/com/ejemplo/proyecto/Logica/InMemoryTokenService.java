package com.ejemplo.proyecto.Logica;

import com.ejemplo.proyecto.persistence.ProcesoSimulacionRepository;
import com.ejemplo.proyecto.persistence.TokenService;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InMemoryTokenService implements TokenService {
    private final AtomicInteger secuencia = new AtomicInteger(1000);
    private final ProcesoSimulacionRepository repository;

    public InMemoryTokenService(ProcesoSimulacionRepository repository) {
        this.repository = repository;
    }

    @Override
    public int generarToken() {
        int token = this.secuencia.getAndIncrement();
        while (this.repository.existeToken(token)) {
            token = this.secuencia.getAndIncrement();
        }
        return token;
    }
}
