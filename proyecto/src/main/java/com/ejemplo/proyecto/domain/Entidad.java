package com.ejemplo.proyecto.domain;

import java.util.Objects;

public abstract class Entidad {
    private int abscisa;
    private int ordenada;

    public Entidad(int x, int y) {
        this.abscisa = x;
        this.ordenada = y;
    }

    public int getX() { return this.abscisa; }
    public int getY() { return this.ordenada; }
    public void setX(int x) { this.abscisa = x; }
    public void setY(int y) { this.ordenada = y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entidad entidad = (Entidad) o;
        return this.abscisa == entidad.abscisa && this.ordenada == entidad.ordenada;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.abscisa, this.ordenada);
    }
}