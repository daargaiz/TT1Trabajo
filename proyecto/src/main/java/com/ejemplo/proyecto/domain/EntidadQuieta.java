package com.ejemplo.proyecto.domain;

/**
 * Representa una entidad estática dentro de la simulación.
 * Las entidades de este tipo no modifican su posición una vez han sido colocadas en el tablero.
 */
public class EntidadQuieta extends Entidad {
	
	/**
     * Construye una nueva EntidadQuieta en las coordenadas especificadas.
     * @param x Posición inicial en el eje de abscisas.
     * @param y Posición inicial en el eje de ordenadas.
     */
	public EntidadQuieta(int x, int y) {
        super(x, y);
    }

    @Override
    public String getTipo() {
        return "quieta";
    }

    @Override
    public String getColor() {
        return "yellow";
    }
}
