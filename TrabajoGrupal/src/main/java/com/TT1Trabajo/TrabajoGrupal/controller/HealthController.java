package com.TT1Trabajo.TrabajoGrupal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controlador de salud para comprobar que el servicio está levantado.
 */
@RestController
public class HealthController {

    /**
     * Devuelve el estado básico de la aplicación.
     * @return Mapa con el estado actual.
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
