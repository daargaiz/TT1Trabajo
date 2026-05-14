package com.ejemplo.proyecto.domain;

/**
* Representa una entidad vírica dentro de la simulación.
* Las entidades de este tipo pueden duplicarse a casillas adyacentes durante la simulación.
*/
public class EntidadVirica extends Entidad {
    private double probabilidadExpansion;
    private static final double PROBABILIDAD_EXPANSION_DEFECTO = 0.25d;
    
	/**
     * Construye una nueva EntidadVirica en las coordenadas especificadas.
     * La entidad se crea con una probabilidad de expansión por defecto.
     * @param x Posición inicial en el eje de abscisas.
     * @param y Posición inicial en el eje de ordenadas.
     */
	public EntidadVirica(int x, int y) {
        this(x, y, PROBABILIDAD_EXPANSION_DEFECTO);
    }

    /**
     * Construye una entidad vírica con una probabilidad de expansión concreta.
     * @param x Posición inicial en el eje de abscisas.
     * @param y Posición inicial en el eje de ordenadas.
     * @param probabilidadExpansion Probabilidad de duplicarse en cada paso.
     */
    public EntidadVirica(int x, int y, double probabilidadExpansion) {
        super(x,y);
        setProbabilidadExpansion(probabilidadExpansion);
    }

    /**
     * Obtiene la probabilidad de expansión de la entidad.
     * @return Valor entre 0 y 1.
     */
    public double getProbabilidadExpansion() {
        return this.probabilidadExpansion;
    }

    /**
     * Actualiza la probabilidad de expansión de la entidad.
     * @param probabilidadExpansion Valor entre 0 y 1.
     */
    public void setProbabilidadExpansion(double probabilidadExpansion) {
        if (probabilidadExpansion < 0.0d || probabilidadExpansion > 1.0d) {
            throw new IllegalArgumentException("La probabilidad de expansion debe estar entre 0 y 1");
        }
        this.probabilidadExpansion = probabilidadExpansion;
    }

    /**
     * Crea una copia de la entidad vírica en una nueva posición.
     * @param x Nueva coordenada horizontal.
     * @param y Nueva coordenada vertical.
     * @return Copia con la misma probabilidad de expansión.
     */
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
