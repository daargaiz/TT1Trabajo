package com.ejemplo.proyecto.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Tablero {
    private int lado;
    private List<Entidad> entidades;

    public Tablero(int lado) {
        this.lado = lado;
        this.entidades = new ArrayList<>();
    }

    public int getLado() { return lado; }
    public void setLado(int lado) { this.lado = lado; }
    public List<Entidad> getEntidades() { return entidades; }
    public void setEntidades(List<Entidad> entidades) {
        this.entidades = entidades;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Tablero t)) return false;
        return this.lado == t.lado;
    }

    @Override
    public int hashCode() { return Objects.hash(lado); }
}
