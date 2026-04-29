package com.ejemplo.proyecto.controller;

import com.ejemplo.proyecto.domain.ProcesoSimulacion;
import com.ejemplo.proyecto.persistence.SolicitudSimulacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<SolicitudResponse> solicitar(
            @RequestParam String nombreUsuario,
            @RequestBody SolicitudRequest request
    ) {
        ProcesoSimulacion proceso = this.solicitudSimulacionService.solicitar(
                nombreUsuario,
                request.getNombreEntidades(),
                request.getCantidadesIniciales()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SolicitudResponse(true, proceso.getSolicitud().getToken(), null, true));
    }

    @GetMapping(value = "/GetSolicitudesUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Integer>> getSolicitudesUsuario(@RequestParam String nombreUsuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.solicitudSimulacionService.listarTokensUsuario(nombreUsuario));
    }

    @GetMapping(value = "/ComprobarSolicitud", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Integer>> comprobarSolicitud(
            @RequestParam String nombreUsuario,
            @RequestParam("tok") int token
    ) {
        List<Integer> respuesta = this.solicitudSimulacionService.comprobarSolicitud(nombreUsuario, token)
                ? List.of(token)
                : List.of();
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
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

    public record SolicitudResponse(boolean done, int tokenSolicitud, String errorMessage, boolean data) {
    }
}
