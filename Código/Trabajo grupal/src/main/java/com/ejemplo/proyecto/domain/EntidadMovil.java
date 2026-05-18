package com.ejemplo.proyecto.domain;

/**
* Representa una entidad móvil dentro de la simulación.
* Las entidades de este tipo modifican su posición a una adyacente en cada instante.
*/
public class EntidadMovil extends Entidad {
    
	/**
     * Construye una nueva EntidadMovil en las coordenadas especificadas.
     * @param x Posición inicial en el eje de abscisas.
     * @param y Posición inicial en el eje de ordenadas.
     */
	public EntidadMovil(int x, int y) {
        super(x, y);
    }

    @Override
    public String getTipo() {
        return "movil";
    }

    @Override
    public String getColor() {
        return "red";
    }
}
