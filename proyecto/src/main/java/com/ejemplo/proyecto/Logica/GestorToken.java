package com.ejemplo.proyecto.Logica;

import org.springframework.stereotype.Component;

@Component
public class GestorToken {
    public long generarSemilla(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("El token es obligatorio");
        }

        long semilla = 1125899906842597L;
        for (char caracter : token.trim().toCharArray()) {
            semilla = 31L * semilla + caracter;
        }
        return semilla;
    }
}
