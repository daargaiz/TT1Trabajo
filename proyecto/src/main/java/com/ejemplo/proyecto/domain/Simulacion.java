package com.ejemplo.proyecto.domain;

import java.util.List;

/**
 * Representa la simulación global que gestiona una colección de tableros.
 * Actúa como contenedor principal para los tableros que forman parte del proceso.
 */
public class Simulacion {
    private List<Tablero> tableros;
    
    /**
     * Construye una nueva instancia de Simulacion con una lista de tableros específica.
     * @param tableros Lista de tableros que forman parte de la simulación.
     */
    public Simulacion(List<Tablero> tableros) {
        this.tableros = tableros;
    }
    
    /**
     * Obtiene la lista de tableros que componen la simulación.
     * @return Lista de tableros actual.
     */
    public List<Tablero> getTableros() {
        return this.tableros;
    }

    /**
     * Actualiza la lista de tableros que componen la simulación.
     * @param tableros Nueva lista de tableros a establecer.
     */
    public void setTableros(List<Tablero> tableros) {
        this.tableros = tableros;
    }
}
