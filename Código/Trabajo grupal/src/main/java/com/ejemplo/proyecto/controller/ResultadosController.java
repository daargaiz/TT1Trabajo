package com.ejemplo.proyecto.controller;

import com.ejemplo.proyecto.domain.ProcesoSimulacion;
import com.ejemplo.proyecto.persistence.SolicitudSimulacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para consultar el resultado de una simulación solicitada.
 */
@RestController
@RequestMapping("/Resultados")
public class ResultadosController {
    private final SolicitudSimulacionService solicitudSimulacionService;

    public ResultadosController(SolicitudSimulacionService solicitudSimulacionService) {
        this.solicitudSimulacionService = solicitudSimulacionService;
    }

    /**
     * Consulta el resultado asociado a un usuario y token.
     * @param nombreUsuario Usuario propietario de la solicitud.
     * @param token Token de la solicitud.
     * @return Resultado formateado si la simulación existe y está completada.
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultsResponse> consultar(
            @RequestParam String nombreUsuario,
            @RequestParam("tok") int token
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.solicitudSimulacionService.consultar(nombreUsuario, token)
                        .map(this::toResponse)
                        .orElseGet(() -> new ResultsResponse(false, token, "Solicitud no encontrada", null)));
    }

    /**
     * Convierte el proceso de dominio en la respuesta esperada por la API.
     */
    private ResultsResponse toResponse(ProcesoSimulacion proceso) {
        if (!proceso.isDone()) {
            return new ResultsResponse(false, proceso.getSolicitud().getToken(), "Solicitud pendiente", null);
        }
        return new ResultsResponse(true, proceso.getSolicitud().getToken(), null, proceso.getResultadoFormateado());
    }

    /**
     * Respuesta de consulta de resultados.
     */
    public record ResultsResponse(boolean done, int tokenSolicitud, String errorMessage, String data) {
    }
}
