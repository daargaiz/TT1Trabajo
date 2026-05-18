package com.ejemplo.proyecto.domain;

/**
 * Representa el ciclo de vida de una simulación solicitada por un usuario.
 * Empieza en estado pendiente y guarda el resultado cuando la ejecución termina.
 */
public class ProcesoSimulacion {
    private final SolicitudSimulacion solicitud;
    private EstadoProcesoSimulacion estado;
    private Tablero tablero;
    private String resultadoFormateado;

    /**
     * Crea un proceso pendiente a partir de una solicitud válida.
     * @param solicitud Solicitud que origina el proceso.
     */
    public ProcesoSimulacion(SolicitudSimulacion solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud es obligatoria");
        }
        this.solicitud = solicitud;
        this.estado = EstadoProcesoSimulacion.PENDIENTE;
    }

    public SolicitudSimulacion getSolicitud() {
        return solicitud;
    }

    public EstadoProcesoSimulacion getEstado() {
        return estado;
    }

    public boolean isDone() {
        return this.estado == EstadoProcesoSimulacion.COMPLETADO;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public String getResultadoFormateado() {
        return resultadoFormateado;
    }

    /**
     * Marca el proceso como completado y almacena el tablero final y su texto.
     * @param tablero Tablero resultante de la simulación.
     * @param resultadoFormateado Representación textual del resultado.
     */
    public void completar(Tablero tablero, String resultadoFormateado) {
        if (tablero == null) {
            throw new IllegalArgumentException("El tablero resultante es obligatorio");
        }
        if (resultadoFormateado == null || resultadoFormateado.isBlank()) {
            throw new IllegalArgumentException("El resultado formateado es obligatorio");
        }
        this.tablero = tablero;
        this.resultadoFormateado = resultadoFormateado;
        this.estado = EstadoProcesoSimulacion.COMPLETADO;
    }
}
