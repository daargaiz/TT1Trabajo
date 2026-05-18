package com.ejemplo.proyecto.controller;

import com.ejemplo.proyecto.domain.EstadoEntidad;
import com.ejemplo.proyecto.domain.Tablero;
import com.ejemplo.proyecto.persistence.GridDataService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para obtener la simulación de grid en texto o JSON.
 */
@RestController
@RequestMapping("/api/grid")
public class GridController {
    private final GridDataService gridDataService;

    public GridController(GridDataService gridDataService) {
        this.gridDataService = gridDataService;
    }

    /**
     * Devuelve la simulación formateada como texto plano.
     * @param tok Token usado como semilla.
     * @param steps Número de pasos de simulación.
     * @return Texto con el historial generado.
     */
    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String getGrid(
            @RequestParam String tok,
            @RequestParam(defaultValue = "18") int steps
    ) {
        return this.gridDataService.generarTexto(tok, steps);
    }

    /**
     * Devuelve la simulación como datos JSON para consumo de clientes.
     * @param tok Token usado como semilla.
     * @param steps Número de pasos de simulación.
     * @return Respuesta con tamaño del tablero e historial de celdas.
     */
    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public GridResponse getGridJson(
            @RequestParam String tok,
            @RequestParam(defaultValue = "18") int steps
    ) {
        Tablero tablero = this.gridDataService.generarTablero(tok, steps);
        return new GridResponse(tablero.getLado(), tablero.getHistorial());
    }

    /**
     * Respuesta JSON del grid generado.
     * @param lado Longitud del lado del tablero.
     * @param celdas Estados de las entidades en el historial.
     */
    public record GridResponse(int lado, List<EstadoEntidad> celdas) {
    }
}
