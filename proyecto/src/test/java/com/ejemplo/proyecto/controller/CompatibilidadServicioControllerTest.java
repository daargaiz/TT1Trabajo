package com.ejemplo.proyecto.controller;

import com.ejemplo.proyecto.Logica.GestorToken;
import com.ejemplo.proyecto.Logica.InMemoryProcesoSimulacionRepository;
import com.ejemplo.proyecto.Logica.InMemoryTokenService;
import com.ejemplo.proyecto.Logica.Printer;
import com.ejemplo.proyecto.Logica.ServicioSolicitudesSimulacion;
import com.ejemplo.proyecto.Logica.TableroFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CompatibilidadServicioControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        InMemoryProcesoSimulacionRepository repository = new InMemoryProcesoSimulacionRepository();
        ServicioSolicitudesSimulacion service = new ServicioSolicitudesSimulacion(
                new GestorToken(),
                new TableroFactory(),
                new Printer(),
                semilla -> new com.ejemplo.proyecto.Logica.Service(new java.util.Random(semilla)),
                repository,
                new InMemoryTokenService(repository)
        );

        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new SolicitudController(service),
                new ResultadosController(service),
                new EmailController()
        ).build();
    }

    @Test
    void permiteSolicitarYDescargarResultadosConElContratoEsperado() throws Exception {
        String solicitudResponse = this.mockMvc.perform(post("/Solicitud/Solicitar")
                        .queryParam("nombreUsuario", "dani")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombreEntidades": ["Prueba1", "Prueba2", "Prueba3"],
                                  "cantidadesIniciales": [1, 2, 1]
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(solicitudResponse.contains("\"done\":true"));
        assertTrue(solicitudResponse.contains("\"tokenSolicitud\":1000"));

        String resultadosResponse = this.mockMvc.perform(post("/Resultados")
                        .queryParam("nombreUsuario", "dani")
                        .queryParam("tok", "1000"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(resultadosResponse.contains("\"done\":true"));
        assertTrue(resultadosResponse.contains("\\n"));
        assertTrue(resultadosResponse.contains("yellow"));
        assertTrue(resultadosResponse.contains("red"));
        assertTrue(resultadosResponse.contains("green"));
    }

    @Test
    void exponeUnEndpointSimpleParaEmail() throws Exception {
        String emailResponse = this.mockMvc.perform(post("/Email")
                        .queryParam("emailAddress", "dani@example.com")
                        .queryParam("message", "hola"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(emailResponse.contains("accepted"));
    }

    @Test
    void devuelveDoneFalseCuandoLaSolicitudEsInvalida() throws Exception {
        String solicitudResponse = this.mockMvc.perform(post("/Solicitud/Solicitar")
                        .queryParam("nombreUsuario", "dani")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombreEntidades": ["inexistente"],
                                  "cantidadesIniciales": [1]
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(solicitudResponse.contains("\"done\":false"));
        assertTrue(solicitudResponse.contains("\"tokenSolicitud\":-1"));
    }

    @Test
    void devuelveDoneFalseCuandoNoExisteElResultado() throws Exception {
        String resultadosResponse = this.mockMvc.perform(post("/Resultados")
                        .queryParam("nombreUsuario", "dani")
                        .queryParam("tok", "9999"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(resultadosResponse.contains("\"done\":false"));
    }
}
