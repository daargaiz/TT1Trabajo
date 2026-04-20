package com.ejemplo.proyecto.domain;

/**
* Representa una entidad vírica dentro de la simulación.
* Las entidades de este tipo pueden duplicarse a casillas adyacentes durante la simulación.
*/
public class EntidadVirica extends Entidad {
    
	/**
     * Construye una nueva EntidadVirica en las coordenadas especificadas.
     * @param x Posición inicial en el eje de abscisas.
     * @param y Posición inicial en el eje de ordenadas.
     */
	public EntidadVirica(int x, int y) {
        super(x,y);
    }
}
