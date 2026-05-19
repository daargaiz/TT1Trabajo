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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                new InMemoryTokenService(repository),
                (nombreUsuario, token) -> {
                }
        );

        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new SolicitudController(service),
                new ResultadosController(service),
                new EmailController()
        ).setControllerAdvice(new ApiExceptionHandler()
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
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(solicitudResponse.contains("\"done\":true"));
        assertTrue(solicitudResponse.contains("\"tokenSolicitud\":1000"));
        assertTrue(solicitudResponse.contains("\"data\":true"));

        String resultadosResponse = this.mockMvc.perform(post("/Resultados")
                        .queryParam("nombreUsuario", "dani")
                        .queryParam("tok", "1000"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(resultadosResponse.contains("\"done\":true"));
        assertTrue(resultadosResponse.contains("\"tokenSolicitud\":1000"));
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
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(emailResponse.contains("\"done\":true"));
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
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(solicitudResponse.contains("Bad Request"));
    }

    @Test
    void devuelveDoneFalseCuandoNoExisteElResultado() throws Exception {
        String resultadosResponse = this.mockMvc.perform(post("/Resultados")
                        .queryParam("nombreUsuario", "dani")
                        .queryParam("tok", "9999"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(resultadosResponse.contains("\"done\":false"));
        assertTrue(resultadosResponse.contains("\"tokenSolicitud\":9999"));
    }

    @Test
    void permiteListarYComprobarSolicitudesDeUnUsuario() throws Exception {
        this.mockMvc.perform(post("/Solicitud/Solicitar")
                        .queryParam("nombreUsuario", "dani")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombreEntidades": ["Prueba1"],
                                  "cantidadesIniciales": [1]
                                }
                                """))
                .andExpect(status().isCreated());
        this.mockMvc.perform(post("/Solicitud/Solicitar")
                        .queryParam("nombreUsuario", "dani")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombreEntidades": ["Prueba2"],
                                  "cantidadesIniciales": [1]
                                }
                                """))
                .andExpect(status().isCreated());

        String tokens = this.mockMvc.perform(get("/Solicitud/GetSolicitudesUsuario")
                        .queryParam("nombreUsuario", "dani"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String comprobacion = this.mockMvc.perform(get("/Solicitud/ComprobarSolicitud")
                        .queryParam("nombreUsuario", "dani")
                        .queryParam("tok", "1000"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("[1000,1001]", tokens);
        assertEquals("[1000]", comprobacion);
    }
}
