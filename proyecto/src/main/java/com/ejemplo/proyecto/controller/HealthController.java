package com.ejemplo.proyecto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador mínimo para comprobar que la aplicación responde.
 */
@RestController
public class HealthController {
    /**
     * Endpoint de salud de la aplicación.
     * @return Cadena fija de confirmación.
     */
    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
