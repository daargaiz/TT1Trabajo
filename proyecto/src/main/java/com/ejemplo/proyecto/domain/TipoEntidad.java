package com.ejemplo.proyecto.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;

/**
 * Define los tipos de entidades disponibles y cómo se construyen desde nombres externos.
 */
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

    /**
     * Crea una entidad del tipo indicado en las coordenadas recibidas.
     * @param x Posición en el eje horizontal.
     * @param y Posición en el eje vertical.
     * @return Nueva entidad concreta del tipo seleccionado.
     */
    public Entidad crearEntidad(int x, int y) {
        return this.factoria.apply(x, y);
    }

    /**
     * Convierte el nombre recibido desde la API al tipo interno de entidad.
     * @param nombre Nombre o alias de la entidad.
     * @return Tipo de entidad correspondiente.
     */
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
