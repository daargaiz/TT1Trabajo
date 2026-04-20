package com.ejemplo.proyecto.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Tablero {
    private int lado;
    private List<Entidad> entidades;
    private final List<EstadoEntidad> historial;

    public Tablero(int lado) {
        this.lado = lado;
        this.entidades = new ArrayList<>();
        this.historial = new ArrayList<>();
    }

    public int getLado() { return lado; }
    public int getAncho() { return lado; }
    public int getAltura() { return lado; }
    public void setLado(int lado) { this.lado = lado; }
    public List<Entidad> getEntidades() { return entidades; }
    public void setEntidades(List<Entidad> entidades) {
        this.entidades = entidades == null ? new ArrayList<>() : new ArrayList<>(entidades);
    }
    public List<EstadoEntidad> getHistorial() { return Collections.unmodifiableList(historial); }

    public boolean estaDentro(Posicion posicion) {
        return posicion.x() >= 0
                && posicion.y() >= 0
                && posicion.x() < this.lado
                && posicion.y() < this.lado;
    }

    public void registrarEstadoInicialSiNecesario() {
        if (this.historial.isEmpty()) {
            registrarInstantanea(0);
        }
    }

    public void registrarInstantanea(int tiempo) {
        for (Entidad entidad : this.entidades) {
            this.historial.add(entidad.registrarEstado(tiempo));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Tablero t)) return false;
        return this.lado == t.lado;
    }

    @Override
    public int hashCode() { return Objects.hash(lado); }

    @Override
    public String toString() { return "Tablero(" + lado + "x" + lado + ")"; }
}
