package com.ejemplo.proyecto.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/**
 * Representa el tablero de la simulación.
 * Gestiona las dimensiones del espacio de juego y mantiene la colección de entidades presentes en él.
 */
public class Tablero {
    private int lado;
    private List<Entidad> entidades;

    /**
     * Construye un nuevo tablero cuadrado con la longitud de lado especificada e inicializa la lista de entidades como una lista vacía.
     * @param lado Longitud del lado del tablero
     */
    public Tablero(int lado) {
        this.lado = lado;
        this.entidades = new ArrayList<>();
    }

    /**
     * Obtiene la longitud del lado del tablero.
     * @return Valor del lado.
     */
    public int getLado() { return lado; }
    
    /**
     * Actualiza la longitud del lado del tablero.
     * @param lado Nueva longitud del lado.
     */
    public void setLado(int lado) { this.lado = lado; }
    
    /**
     * Obtiene la lista de entidades presentes en el tablero.
     * @return Lista de entidades.
     */
    public List<Entidad> getEntidades() { return entidades; }
    
    /**
     * Actualiza la lista de entidades presentes en el tablero.
     * @param entidades Lista de entidades a asignar.
     */
    public void setEntidades(List<Entidad> entidades) { this.entidades = entidades; }

    /**
     * Compara este tablero con otro objeto para determinar si son equivalentes; es decir, si ambos tableros tienen el mismo tamaño de lado.
     * @param obj Objeto con el que comparar.
     * @return true si ambos tableros tienen el mismo lado, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Tablero t)) return false;
        return this.lado == t.lado;
    }

    /**
     * Genera un código hash basado en la dimensión del lado del tablero.
     * @return Código hash calculado.
     */
    @Override
    public int hashCode() { return Objects.hash(lado); }
}