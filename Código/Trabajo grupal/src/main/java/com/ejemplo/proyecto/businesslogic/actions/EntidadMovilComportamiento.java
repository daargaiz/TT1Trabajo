package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.EntidadMovil;
import com.ejemplo.proyecto.domain.Posicion;

import java.util.ArrayList;
import java.util.List;
/**
 * Implementación base del comportamiento de una entidad móvil en el tablero.
 *
 * <p>Define cómo se mueve una entidad y su representación visual mediante color.
 * Las subclases deben sobreescribir los métodos para proporcionar comportamiento concreto.
 *
 * @see IEntidadMovilComportamiento
 * @see EntidadMovil
 */
public class EntidadMovilComportamiento implements IEntidadMovilComportamiento {
    /**
     * Mueve la entidad dentro de los límites del tablero.
     *
     * @param entidad          la entidad que se desea mover
     * @param dimensionTablero la longitud lado del tablero que delimita el movimiento
     * @return la entidad con su posición actualizada
     * @throws UnsupportedOperationException si la subclase no ha implementado este método
     */
    @Override
    public EntidadMovil moverse(com.ejemplo.proyecto.domain.Entidad entidad, int dimensionTablero) {
        if (entidad == null) {
            throw new NullPointerException("La entidad no puede ser nula");
        }
        if (!(entidad instanceof EntidadMovil movil)) {
            return null;
        }
        if (!estaDentro(movil.getX(), movil.getY(), dimensionTablero)) {
            return null;
        }

        List<Posicion> candidatas = new ArrayList<>();
        agregarSiDentro(candidatas, movil.getX() + 1, movil.getY(), dimensionTablero);
        agregarSiDentro(candidatas, movil.getX(), movil.getY() + 1, dimensionTablero);
        agregarSiDentro(candidatas, movil.getX() - 1, movil.getY(), dimensionTablero);
        agregarSiDentro(candidatas, movil.getX(), movil.getY() - 1, dimensionTablero);

        if (candidatas.isEmpty()) {
            return null;
        }

        Posicion destino = candidatas.get(0);
        return new EntidadMovil(destino.x(), destino.y());
    }
    /**
     * Devuelve el color asociado a esta entidad para su representación visual.
     *
     * @return cadena con el nombre o código del color (por ejemplo, {@code "RED"} o {@code "#FF0000"})
     * @throws UnsupportedOperationException si la subclase no ha implementado este método
     */
    @Override
    public String getColor() {
        return "red";
    }

    private void agregarSiDentro(List<Posicion> candidatas, int x, int y, int dimensionTablero) {
        if (estaDentro(x, y, dimensionTablero)) {
            candidatas.add(new Posicion(x, y));
        }
    }

    private boolean estaDentro(int x, int y, int dimensionTablero) {
        return x >= 0 && y >= 0 && x < dimensionTablero && y < dimensionTablero;
    }
}
