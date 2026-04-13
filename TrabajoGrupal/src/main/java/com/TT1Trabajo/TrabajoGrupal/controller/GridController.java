package com.TT1Trabajo.TrabajoGrupal.controller;
import com.TT1Trabajo.TrabajoGrupal.service.GridService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GridController {

    private final GridService gridService;

    public GridController(GridService gridService) {
        this.gridService = gridService;
    }

    @GetMapping("/api/grid")
    public String getGrid(@RequestParam String tok) {
        return gridService.getGridData(tok);
    }
}