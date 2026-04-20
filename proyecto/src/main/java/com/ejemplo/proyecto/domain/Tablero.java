package com.ejemplo.proyecto.domain;

import java.util.*;

public class Tablero {
    private int lado;
    private List<Entidad> entidades;

    public Tablero(int lado) {
        this.lado = lado;
        this.entidades = new ArrayList<>();  // fix del bug
    }

    public int getLado() { return lado; }
    public void setLado(int lado) { this.lado = lado; }
    public List<Entidad> getEntidades() { return entidades; }
    public void setEntidades(List<Entidad> entidades) { this.entidades = entidades; }

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