package com.ejemplo.proyecto.domain;

/**
* Representa una entidad vírica dentro de la simulación.
* Las entidades de este tipo pueden duplicarse a casillas adyacentes durante la simulación.
*/
public class EntidadVirica extends Entidad {
    private double probabilidadExpansion;
    
	/**
     * Construye una nueva EntidadVirica en las coordenadas especificadas.
     * @param x Posición inicial en el eje de abscisas.
     * @param y Posición inicial en el eje de ordenadas.
     */
	public EntidadVirica(int x, int y) {
        this(x, y, 1.0d);
    }

    public EntidadVirica(int x, int y, double probabilidadExpansion) {
        super(x, y);
        setProbabilidadExpansion(probabilidadExpansion);
    }

    public double getProbabilidadExpansion() {
        return this.probabilidadExpansion;
    }

    public void setProbabilidadExpansion(double probabilidadExpansion) {
        if (probabilidadExpansion < 0.0d || probabilidadExpansion > 1.0d) {
            throw new IllegalArgumentException("La probabilidad de expansion debe estar entre 0 y 1");
        }
        this.probabilidadExpansion = probabilidadExpansion;
    }

    public EntidadVirica copiarEn(int x, int y) {
        return new EntidadVirica(x, y, this.probabilidadExpansion);
    }

    @Override
    public String getTipo() {
        return "virica";
    }

    @Override
    public String getColor() {
        return "green";
    }
}
