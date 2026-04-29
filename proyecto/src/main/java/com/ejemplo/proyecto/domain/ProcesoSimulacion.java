package com.ejemplo.proyecto.domain;

public class ProcesoSimulacion {
    private final SolicitudSimulacion solicitud;
    private EstadoProcesoSimulacion estado;
    private Tablero tablero;
    private String resultadoFormateado;

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
