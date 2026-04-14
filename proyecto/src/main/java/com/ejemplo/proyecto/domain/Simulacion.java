package com.ejemplo.proyecto.domain;

import java.util.List;

public class Simulacion {
    private List<Tablero> tableros;
    public Simulacion(List<Tablero> tableros) {
        this.tableros = tableros;
    }
    public List<Tablero> getTableros() {
        return this.tableros;
    }

    public void setTableros(List<Tablero> tableros) {
        this.tableros = tableros;
    }
}
