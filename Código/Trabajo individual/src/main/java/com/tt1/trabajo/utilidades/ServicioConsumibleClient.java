package com.tt1.trabajo.utilidades;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class ServicioConsumibleClient {

    private final RestClient restClient;
    private final String usuario;

    public ServicioConsumibleClient(
            @Value("${servicio.consumible.base-url}") String baseUrl,
            @Value("${servicio.usuario}") String usuario) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
        this.usuario = usuario;
    }

    public boolean enviarEmail(String emailAddress, String message) {
        try {
            Object respuesta = restClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/Email")
                            .queryParam("emailAddress", emailAddress)
                            .queryParam("message", message)
                            .build())
                    .retrieve()
                    .body(String.class);
            return respuesta != null;
        } catch (RestClientResponseException ignored) {
            return false;
        }
    }

    public int solicitarSimulation(List<String> nombresEntidades, List<Integer> cantidadesIniciales) {
        try {
            SolicitudRequest solicitud = new SolicitudRequest();
            solicitud.setNombreEntidades(nombresEntidades);
            solicitud.setCantidadesIniciales(cantidadesIniciales);

            SolicitudResponse respuesta = restClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/Solicitud/Solicitar")
                            .queryParam("nombreUsuario", usuario)
                            .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(solicitud)
                    .retrieve()
                    .body(SolicitudResponse.class);

            if (respuesta == null || !respuesta.isDone()) {
                return -1;
            }

            return respuesta.getTokenSolicitud();
        } catch (RestClientResponseException ignored) {
            return -1;
        }
    }

    public String descargarResultados(int token) {
        try {
            ResultsResponse respuesta = restClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/Resultados")
                            .queryParam("nombreUsuario", usuario)
                            .queryParam("tok", token)
                            .build())
                    .retrieve()
                    .body(ResultsResponse.class);

            if (respuesta == null || !respuesta.isDone()) {
                return "";
            }

            return respuesta.getData() == null ? "" : respuesta.getData();
        } catch (RestClientResponseException ignored) {
            return "";
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

    public static class SolicitudResponse {
        private boolean done;
        private int tokenSolicitud;

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }

        public int getTokenSolicitud() {
            return tokenSolicitud;
        }

        public void setTokenSolicitud(int tokenSolicitud) {
            this.tokenSolicitud = tokenSolicitud;
        }
    }

    public static class ResultsResponse {
        private boolean done;
        private String data;

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
