package com.ejemplo.proyecto.Logica;

import org.springframework.stereotype.Component;

/**
 * Convierte tokens o cadenas de entrada en semillas numéricas reproducibles.
 */
@Component
public class GestorToken {
    /**
     * Genera una semilla estable a partir del texto recibido.
     * @param token Texto base para el cálculo.
     * @return Valor numérico usado como semilla.
     */
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
