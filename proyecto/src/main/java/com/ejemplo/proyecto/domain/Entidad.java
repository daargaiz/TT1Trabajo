package com.ejemplo.proyecto.domain;

import java.util.Objects;

/**
 * Clase abstracta que define las propiedades básicas de cualquier entidad.
 */
public abstract class Entidad {
    private int abscisa;
    private int ordenada;

    /**
     * Construye una nueva Entidad en las coordenadas especificadas.
     * @param x Posición inicial en el eje de abscisas.
     * @param y Posición inicial en el eje de ordenadas.
     */
    public Entidad(int x, int y) {
        this.abscisa = x;
        this.ordenada = y;
    }
    
    /**
     * Obtiene la posición horizontal de la entidad.
     * @return Valor actual de las abscisas.
     */
    public int getX() { return this.abscisa; }
    
    /**
     * Obtiene la posición vertical de la entidad.
     * @return Valor actual de las ordenadas.
     */
    public int getY() { return this.ordenada; }
    
    /**
     * Actualiza la posición horizontal de la entidad.
     * @param x Nueva coordenada en el eje de las abscisas.
     */
    public void setX(int x) { this.abscisa = x; }
    
    /**
     * Actualiza la posición vertical de la entidad.
     * @param y Nueva coordenada en el eje de las ordenadas.
     */
    public void setY(int y) { this.ordenada = y; }

    /**
     * Obtiene la posición actual de la entidad.
     * @return Posición inmutable con las coordenadas actuales.
     */
    public Posicion getPosicion() {
        return new Posicion(this.abscisa, this.ordenada);
    }

    /**
     * Desplaza la entidad a una nueva posición.
     * @param posicion Nueva posición dentro del tablero.
     */
    public void moverA(Posicion posicion) {
        this.abscisa = posicion.x();
        this.ordenada = posicion.y();
    }

    /**
     * Construye el estado serializable de la entidad para un instante concreto.
     * @param tiempo Instante de la simulación.
     * @return Estado exportable de la entidad.
     */
    public EstadoEntidad registrarEstado(int tiempo) {
        return new EstadoEntidad(tiempo, this.abscisa, this.ordenada, getColor());
    }

    /**
     * Obtiene el nombre lógico del tipo de entidad.
     * @return Tipo que se usa en la lógica y serialización.
     */
    public abstract String getTipo();

    /**
     * Obtiene el color de representación de la entidad.
     * @return Color asociado a este tipo de entidad.
     */
    public abstract String getColor();

    /**
     * Compara esta entidad con otro objeto para determinar si son equivalentes; es decir, es una entidad con las mismas coordenadas.
     * @param o Objeto con el que comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entidad entidad = (Entidad) o;
        return this.abscisa == entidad.abscisa && this.ordenada == entidad.ordenada;
    }

    /**
     * Calcula un código hash único para la entidad.
     * @return Código hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.abscisa, this.ordenada);
    }
}
