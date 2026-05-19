package com.ejemplo.proyecto.rabbit;

public record SolicitudSimulacionMessage(
        String nombreUsuario,
        int token
) {
}
