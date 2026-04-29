package com.ejemplo.proyecto.controller;

import com.ejemplo.proyecto.Logica.DefaultSimulacionServiceFactory;
import com.ejemplo.proyecto.Logica.GestorToken;
import com.ejemplo.proyecto.Logica.GridGenerator;
import com.ejemplo.proyecto.Logica.Printer;
import com.ejemplo.proyecto.Logica.TableroFactory;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GridControllerTest {
    @Test
    void devuelveTextoEnFormatoLegado() throws Exception {
        String body = mockMvc().perform(get("/api/grid").param("tok", "demo-token"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(body.startsWith("12\n"));
    }

    @Test
    void devuelveJsonConLadoYCeldas() throws Exception {
        String body = mockMvc().perform(get("/api/grid/json").param("tok", "demo-token"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(body.contains("\"lado\":12"));
        assertTrue(body.contains("\"celdas\""));
    }

    private MockMvc mockMvc() {
        GridGenerator gridGenerator = new GridGenerator(
                new GestorToken(),
                new TableroFactory(),
                new Printer(),
                new DefaultSimulacionServiceFactory()
        );
        return MockMvcBuilders.standaloneSetup(new GridController(gridGenerator)).build();
    }
}
