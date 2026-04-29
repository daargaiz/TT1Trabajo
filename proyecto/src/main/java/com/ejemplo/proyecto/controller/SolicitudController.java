package com.ejemplo.proyecto.controller;

import com.ejemplo.proyecto.domain.ProcesoSimulacion;
import com.ejemplo.proyecto.persistence.SolicitudSimulacionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Solicitud")
public class SolicitudController {
    private final SolicitudSimulacionService solicitudSimulacionService;

    public SolicitudController(SolicitudSimulacionService solicitudSimulacionService) {
        this.solicitudSimulacionService = solicitudSimulacionService;
    }

    @PostMapping(value = "/Solicitar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SolicitudResponse solicitar(
            @RequestParam String nombreUsuario,
            @RequestBody SolicitudRequest request
    ) {
        try {
            ProcesoSimulacion proceso = this.solicitudSimulacionService.solicitar(
                    nombreUsuario,
                    request.getNombreEntidades(),
                    request.getCantidadesIniciales()
            );
            return new SolicitudResponse(true, proceso.getSolicitud().getToken());
        } catch (IllegalArgumentException ignored) {
            return new SolicitudResponse(false, -1);
        }
    }

    public static class SolicitudRequest {
        private List<Integer> cantidadesIniciales;
        private List<String> nombreEntidades;

        public List<Integer> getCantidadesIniciales() {
            return cantidadesIniciales;
        }

        public void setCantidadesIniciales(List<Integer> cantidadesIniciales) {
            this.cantidadesIniciales = cantidadesIniciales;
        }

        public List<String> getNombreEntidades() {
            return nombreEntidades;
        }

        public void setNombreEntidades(List<String> nombreEntidades) {
            this.nombreEntidades = nombreEntidades;
        }
    }

    public record SolicitudResponse(boolean done, int tokenSolicitud) {
    }
}
