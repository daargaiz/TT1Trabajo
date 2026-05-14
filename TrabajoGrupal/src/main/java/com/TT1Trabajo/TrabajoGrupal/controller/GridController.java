package com.TT1Trabajo.TrabajoGrupal.controller;
import com.TT1Trabajo.TrabajoGrupal.service.GridService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para consultar los datos del grid.
 */
@RestController
public class GridController {

    private final GridService gridService;

    public GridController(GridService gridService) {
        this.gridService = gridService;
    }

    /**
     * Devuelve los datos del grid asociados al token recibido.
     * @param tok Token usado para identificar o generar el grid.
     * @return Datos del grid en formato texto.
     */
    @GetMapping("/api/grid")
    public String getGrid(@RequestParam String tok) {
        return gridService.getGridData(tok);
    }
}
