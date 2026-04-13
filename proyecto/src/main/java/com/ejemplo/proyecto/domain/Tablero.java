package com.ejemplo.proyecto.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Tablero {
    private int altura;
    private int ancho;
    private List<Entidad> entidades;

    public Tablero(int altura, int ancho) {
        this.altura = altura;
        this.ancho = ancho;
        this.entidades = new HashMap<>();
    }

    public int getAltura() {
        return altura;
    }
    public int getAncho() {
        return ancho;
    }
    public void setAltura(int altura) {
        this.altura = altura;
    }
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }
    public Map<Posicion, Entidad> getEntidades() {
        return this.entidades;
    }
    public void setEntidades(Map<Posicion, Entidad> entidades) {
        this.entidades = entidades;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Tablero t)) return false;
        return this.ancho == t.ancho && this.altura == t.altura;
    }

    @Override
    public int hashCode() {
        return Objects.hash(altura, ancho);
    }

    @Override
    public String toString() {
        return "Tablero(" + ancho + "x" + altura + ")";
    }
}