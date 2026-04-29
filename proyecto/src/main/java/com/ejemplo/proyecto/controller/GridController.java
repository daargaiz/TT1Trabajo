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

@RestController
@RequestMapping("/api/grid")
public class GridController {
    private final GridDataService gridDataService;

    public GridController(GridDataService gridDataService) {
        this.gridDataService = gridDataService;
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String getGrid(
            @RequestParam String tok,
            @RequestParam(defaultValue = "3") int steps
    ) {
        return this.gridDataService.generarTexto(tok, steps);
    }

    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public GridResponse getGridJson(
            @RequestParam String tok,
            @RequestParam(defaultValue = "3") int steps
    ) {
        Tablero tablero = this.gridDataService.generarTablero(tok, steps);
        return new GridResponse(tablero.getLado(), tablero.getHistorial());
    }

    public record GridResponse(int lado, List<EstadoEntidad> celdas) {
    }
}
