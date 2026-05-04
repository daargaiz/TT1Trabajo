package com.ejemplo.proyecto.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;

public enum TipoEntidad {
    QUIETA("quieta", List.of("quieta", "prueba1"), EntidadQuieta::new),
    MOVIL("movil", List.of("movil", "prueba2"), EntidadMovil::new),
    VIRICA("virica", List.of("virica", "prueba3"), EntidadVirica::new);

    private final String nombreExterno;
    private final List<String> alias;
    private final BiFunction<Integer, Integer, Entidad> factoria;

    TipoEntidad(
            String nombreExterno,
            List<String> alias,
            BiFunction<Integer, Integer, Entidad> factoria
    ) {
        this.nombreExterno = nombreExterno;
        this.alias = alias;
        this.factoria = factoria;
    }

    public String getNombreExterno() {
        return nombreExterno;
    }

    public Entidad crearEntidad(int x, int y) {
        return this.factoria.apply(x, y);
    }

    public static TipoEntidad desdeNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de entidad es obligatorio");
        }

        String normalizado = nombre.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(tipo -> tipo.alias.contains(normalizado))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de entidad desconocido: " + nombre));
    }
}
