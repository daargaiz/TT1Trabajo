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

@RestController
@RequestMapping("/Resultados")
public class ResultadosController {
    private final SolicitudSimulacionService solicitudSimulacionService;

    public ResultadosController(SolicitudSimulacionService solicitudSimulacionService) {
        this.solicitudSimulacionService = solicitudSimulacionService;
    }

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

    private ResultsResponse toResponse(ProcesoSimulacion proceso) {
        if (!proceso.isDone()) {
            return new ResultsResponse(false, proceso.getSolicitud().getToken(), "Solicitud pendiente", null);
        }
        return new ResultsResponse(true, proceso.getSolicitud().getToken(), null, proceso.getResultadoFormateado());
    }

    public record ResultsResponse(boolean done, int tokenSolicitud, String errorMessage, String data) {
    }
}
