package com.ejemplo.proyecto.Logica;

import com.ejemplo.proyecto.domain.Entidad;
import com.ejemplo.proyecto.domain.EntidadMovil;
import com.ejemplo.proyecto.domain.EntidadQuieta;
import com.ejemplo.proyecto.domain.EntidadVirica;
import com.ejemplo.proyecto.domain.Posicion;
import com.ejemplo.proyecto.domain.Simulacion;
import com.ejemplo.proyecto.domain.Tablero;
import com.ejemplo.proyecto.persistence.SimulacionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Servicio principal de simulación.
 * Aplica el movimiento de entidades móviles y la expansión de entidades víricas paso a paso.
 */
public class Service implements SimulacionService {
    private static final List<Posicion> DESPLAZAMIENTOS = List.of(
            new Posicion(1, 0),
            new Posicion(0, 1),
            new Posicion(-1, 0),
            new Posicion(0, -1)
    );
    private final Random random;

    /**
     * Crea el servicio con una semilla fija para obtener simulaciones repetibles por defecto.
     */
    public Service() {
        this(new Random(0L));
    }

    /**
     * Crea el servicio con el generador aleatorio indicado.
     * @param random Generador usado para escoger movimientos y expansiones.
     */
    public Service(Random random) {
        this.random = random;
    }

    /**
     * Ejecuta un único instante de la simulación sobre el tablero recibido.
     * @param tablero Tablero que se actualiza con el nuevo estado.
     */
    @Override
    public void nextStep(Tablero tablero) {
        tablero.registrarEstadoInicialSiNecesario();

        List<Entidad> entidadesOriginales = new ArrayList<>(tablero.getEntidades());
        Set<Posicion> ocupadasAlInicio = extraerPosiciones(entidadesOriginales);
        Set<Posicion> ocupadasAlFinal = new LinkedHashSet<>();
        List<Entidad> entidadesFinales = new ArrayList<>();

        for (Entidad entidad : entidadesOriginales) {
            if (entidad instanceof EntidadQuieta || entidad instanceof EntidadVirica) {
                entidadesFinales.add(entidad);
                ocupadasAlFinal.add(entidad.getPosicion());
            }
        }

        for (Entidad entidad : entidadesOriginales) {
            if (entidad instanceof EntidadMovil movil) {
                resolverMovimiento(tablero, movil, ocupadasAlInicio, ocupadasAlFinal, entidadesFinales);
            } else if (entidad instanceof EntidadVirica virica) {
                resolverExpansionVirica(tablero, virica, ocupadasAlInicio, ocupadasAlFinal, entidadesFinales);
            }
        }

        tablero.setEntidades(entidadesFinales);
        tablero.registrarInstantanea(calcularSiguienteTiempo(tablero));
    }

    /**
     * Ejecuta varios pasos de simulación sobre el tablero recibido.
     * @param tablero Tablero inicial que se va actualizando.
     * @param steps Número de pasos a ejecutar.
     * @return Simulación con el tablero resultante.
     */
    @Override
    public Simulacion ejecutarSimulacion(Tablero tablero, int steps) {
        if (steps < 0) {
            throw new IllegalArgumentException("El numero de pasos no puede ser negativo");
        }

        for (int i = 0; i < steps; i++) {
            nextStep(tablero);
        }

        return new Simulacion(List.of(tablero));
    }

    /**
     * Resuelve el desplazamiento de una entidad móvil evitando posiciones ocupadas.
     */
    private void resolverMovimiento(
            Tablero tablero,
            EntidadMovil entidad,
            Set<Posicion> ocupadasAlInicio,
            Set<Posicion> ocupadasAlFinal,
            List<Entidad> entidadesFinales
    ) {
        Posicion destino = buscarDestinoLibre(tablero, entidad.getPosicion(), ocupadasAlInicio, ocupadasAlFinal);
        if (destino == null) {
            destino = entidad.getPosicion();
        } else {
            entidad.moverA(destino);
        }

        entidadesFinales.add(entidad);
        ocupadasAlFinal.add(destino);
    }

    /**
     * Resuelve la posible duplicación de una entidad vírica en una celda libre.
     */
    private void resolverExpansionVirica(
            Tablero tablero,
            EntidadVirica entidad,
            Set<Posicion> ocupadasAlInicio,
            Set<Posicion> ocupadasAlFinal,
            List<Entidad> entidadesFinales
    ) {
        if (entidad.getProbabilidadExpansion() <= 0.0d) {
            return;
        }
        if (entidad.getProbabilidadExpansion() < 1.0d
                && this.random.nextDouble() > entidad.getProbabilidadExpansion()) {
            return;
        }

        Posicion destino = buscarDestinoLibre(tablero, entidad.getPosicion(), ocupadasAlInicio, ocupadasAlFinal);
        if (destino == null) {
            return;
        }

        Entidad duplicado = entidad.copiarEn(destino.x(), destino.y());
        entidadesFinales.add(duplicado);
        ocupadasAlFinal.add(destino);
    }

    /**
     * Busca una posición adyacente libre dentro del tablero.
     */
    private Posicion buscarDestinoLibre(
            Tablero tablero,
            Posicion origen,
            Set<Posicion> ocupadasAlInicio,
            Set<Posicion> ocupadasAlFinal
    ) {
        List<Posicion> candidatas = new ArrayList<>();

        for (Posicion desplazamiento : DESPLAZAMIENTOS) {
            Posicion candidata = new Posicion(
                    origen.x() + desplazamiento.x(),
                    origen.y() + desplazamiento.y()
            );

            if (!tablero.estaDentro(candidata)) {
                continue;
            }
            if (ocupadasAlInicio.contains(candidata)) {
                continue;
            }
            if (ocupadasAlFinal.contains(candidata)) {
                continue;
            }

            candidatas.add(candidata);
        }

        if (candidatas.isEmpty()) {
            return null;
        }

        Collections.shuffle(candidatas, this.random);
        return candidatas.get(0);
    }

    private Set<Posicion> extraerPosiciones(List<Entidad> entidades) {
        Set<Posicion> posiciones = new LinkedHashSet<>();
        for (Entidad entidad : entidades) {
            posiciones.add(entidad.getPosicion());
        }
        return posiciones;
    }

    private int calcularSiguienteTiempo(Tablero tablero) {
        return tablero.getHistorial().stream()
                .mapToInt(estado -> estado.tiempo())
                .max()
                .orElse(-1) + 1;
    }
}
