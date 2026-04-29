package com.ejemplo.proyecto.businesslogic.actions;

import com.ejemplo.proyecto.domain.Entidad;
import com.ejemplo.proyecto.domain.EntidadVirica;
import com.ejemplo.proyecto.domain.Posicion;

import java.util.ArrayList;
import java.util.List;
/**
 * Implementación del comportamiento de una entidad vírica en el tablero.
 *
 * <p>A diferencia de {@link EntidadMovilComportamiento} y {@link EntidadQuietaComportamiento},
 * esta clase representa entidades con capacidad de propagación: al moverse pueden
 * generar múltiples entidades nuevas, modelando un comportamiento de tipo vírico o expansivo.
 *
 * @see IEntidadViricaComportamiento
 * @see Entidad
 */
public class EntidadViricaComportamiento implements IEntidadViricaComportamiento {
    /**
     * Propaga la entidad vírica por el tablero, pudiendo generar múltiples entidades resultado.
     *
     * <p>A diferencia del movimiento convencional, este método devuelve una lista que puede
     * contener la entidad original desplazada, nuevas entidades generadas por propagación,
     * o ambas. La lista vacía indicaría que la entidad no ha sobrevivido ni se ha propagado.
     *
     * @param entidad          la entidad vírica que se propaga
     * @param dimensionTablero el tamaño del tablero que delimita la propagación
     * @return lista de entidades resultantes de la propagación; nunca {@code null}
     * @throws UnsupportedOperationException si la subclase no ha implementado este método
     */
    @Override
    public List<Entidad> moverse(Entidad entidad, int dimensionTablero) {
        if (entidad == null) {
            throw new NullPointerException("La entidad no puede ser nula");
        }
        if (!(entidad instanceof EntidadVirica virica)) {
            return null;
        }
        if (!estaDentro(virica.getX(), virica.getY(), dimensionTablero)) {
            return null;
        }

        List<Entidad> resultado = new ArrayList<>();
        resultado.add(virica);

        Posicion copia = buscarDestinoAdyacente(virica.getX(), virica.getY(), dimensionTablero);
        if (copia != null) {
            resultado.add(new EntidadVirica(copia.x(), copia.y(), virica.getProbabilidadExpansion()));
        }

        return resultado;
    }
    /**
     * Devuelve el color asociado a esta entidad para su representación visual.
     *
     * @return cadena con el nombre o código del color (por ejemplo, {@code "RED"} o {@code "#FF0000"})
     * @throws UnsupportedOperationException si la subclase no ha implementado este método
     */
    @Override
    public String getColor() {
        return "green";
    }

    private Posicion buscarDestinoAdyacente(int x, int y, int dimensionTablero) {
        if (estaDentro(x + 1, y, dimensionTablero)) {
            return new Posicion(x + 1, y);
        }
        if (estaDentro(x, y + 1, dimensionTablero)) {
            return new Posicion(x, y + 1);
        }
        if (estaDentro(x - 1, y, dimensionTablero)) {
            return new Posicion(x - 1, y);
        }
        if (estaDentro(x, y - 1, dimensionTablero)) {
            return new Posicion(x, y - 1);
        }
        return null;
    }

    private boolean estaDentro(int x, int y, int dimensionTablero) {
        return x >= 0 && y >= 0 && x < dimensionTablero && y < dimensionTablero;
    }
}
