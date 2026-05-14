package com.ejemplo.proyecto.businesslogic;

import com.ejemplo.proyecto.Logica.GestorToken;
import com.ejemplo.proyecto.Logica.InMemoryProcesoSimulacionRepository;
import com.ejemplo.proyecto.Logica.InMemoryTokenService;
import com.ejemplo.proyecto.Logica.Printer;
import com.ejemplo.proyecto.Logica.ServicioSolicitudesSimulacion;
import com.ejemplo.proyecto.Logica.TableroFactory;
import com.ejemplo.proyecto.domain.EstadoProcesoSimulacion;
import com.ejemplo.proyecto.domain.ProcesoSimulacion;
import com.ejemplo.proyecto.domain.TipoEntidad;
import com.ejemplo.proyecto.persistence.SolicitudSimulacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolicitudSimulacionServiceTest {
    private SolicitudSimulacionService service;

    @BeforeEach
    void setUp() {
        InMemoryProcesoSimulacionRepository repository = new InMemoryProcesoSimulacionRepository();
        service = new ServicioSolicitudesSimulacion(
                new GestorToken(),
                new TableroFactory(),
                new Printer(),
                semilla -> new com.ejemplo.proyecto.Logica.Service(new java.util.Random(semilla)),
                repository,
                new InMemoryTokenService(repository)
        );
    }

    @Test
    void procesaSolicitudConElContratoDelClienteAnterior() {
        ProcesoSimulacion proceso = service.solicitar(
                "dani",
                List.of("Prueba1", "Prueba2", "Prueba3"),
                List.of(1, 2, 1)
        );

        assertEquals(EstadoProcesoSimulacion.COMPLETADO, proceso.getEstado());
        assertTrue(proceso.isDone());
        assertTrue(proceso.getResultadoFormateado().startsWith("12\n"));
        assertTrue(proceso.getResultadoFormateado().contains(",yellow"));
        assertTrue(proceso.getResultadoFormateado().contains(",red"));
        assertTrue(proceso.getResultadoFormateado().contains(",green"));
    }

    @Test
    void guardaYRecuperaElProcesoPorUsuarioYToken() {
        ProcesoSimulacion creado = service.solicitar(
                "dani",
                List.of("quieta", "movil"),
                List.of(1, 1)
        );

        ProcesoSimulacion recuperado = service.consultar("dani", creado.getSolicitud().getToken()).orElseThrow();

        assertEquals(creado.getSolicitud().getToken(), recuperado.getSolicitud().getToken());
        assertEquals(creado.getResultadoFormateado(), recuperado.getResultadoFormateado());
    }

    @Test
    void normalizaTiposDuplicadosAntesDeSimular() {
        ProcesoSimulacion proceso = service.solicitar(
                "dani",
                List.of("Prueba2", "movil", "Prueba1"),
                List.of(2, 3, 1)
        );

        Map<TipoEntidad, Integer> cantidades = proceso.getSolicitud().getEspecificaciones().stream()
                .collect(java.util.stream.Collectors.toMap(
                        especificacion -> especificacion.tipo(),
                        especificacion -> especificacion.cantidad()
                ));

        assertEquals(5, cantidades.get(TipoEntidad.MOVIL));
        assertEquals(1, cantidades.get(TipoEntidad.QUIETA));
    }

    @Test
    void devuelveVacioCuandoElTokenNoExiste() {
        assertFalse(service.consultar("dani", 9999).isPresent());
    }

    @Test
    void generaTokensDistintosEnSolicitudesConsecutivas() {
        ProcesoSimulacion primero = service.solicitar(
                "dani",
                List.of("quieta"),
                List.of(1)
        );
        ProcesoSimulacion segundo = service.solicitar(
                "dani",
                List.of("quieta"),
                List.of(1)
        );

        assertTrue(segundo.getSolicitud().getToken() > primero.getSolicitud().getToken());
    }

    @Test
    void rechazaSolicitudesSinEntidadesValidas() {
        assertThrows(IllegalArgumentException.class, () -> service.solicitar(
                "dani",
                List.of("quieta"),
                List.of(0)
        ));
    }

    @Test
    void listaLosTokensDelUsuario() {
        service.solicitar("dani", List.of("quieta"), List.of(1));
        service.solicitar("dani", List.of("movil"), List.of(1));

        assertEquals(List.of(1000, 1001), service.listarTokensUsuario("dani"));
    }

    @Test
    void compruebaSiUnaSolicitudExisteYEstaLista() {
        ProcesoSimulacion proceso = service.solicitar("dani", List.of("quieta"), List.of(1));

        assertTrue(service.comprobarSolicitud("dani", proceso.getSolicitud().getToken()));
        assertFalse(service.comprobarSolicitud("dani", 9999));
    }
    
    @Test
    @DisplayName("solicitar: lanzar excepción si las listas de nombres y cantidades tienen distinto tamaño")
    void testListasDescompensadas() {
        // Si mandas 2 nombres pero solo 1 cantidad, el servicio debería fallar
        assertThrows(IllegalArgumentException.class, () -> service.solicitar(
                "juan",
                List.of("movil", "quieta"),
                List.of(1) // Falta la cantidad para 'quieta'
        ));
    }

    @Test
    @DisplayName("solicitar: verificar que el resultado formateado contiene la estructura correcta")
    void testContenidoResultadoFormateado() {
        ProcesoSimulacion proceso = service.solicitar(
                "juan",
                List.of("movil"),
                List.of(1)
        );
        
        String resultado = proceso.getResultadoFormateado();
        
        // Verificamos que contenga los elementos del contrato (tiempo,y,x,color)
        assertAll(
            () -> assertTrue(resultado.contains("red"), "Debe contener el color de la entidad móvil"),
            () -> assertTrue(resultado.lines().count() > 1, "Debe tener al menos la cabecera y una entidad")
        );
    }

    @Test
    @DisplayName("consultar: no debe devolver nada si el usuario no coincide con el token")
    void testUsuarioNoCoincideConToken() {
        ProcesoSimulacion creado = service.solicitar("juan", List.of("quieta"), List.of(1));
        
        // Intentamos consultar el token de 'juan' usando el usuario 'pedro'
        Optional<ProcesoSimulacion> resultado = service.consultar("pedro", creado.getSolicitud().getToken());
        
        assertTrue(resultado.isEmpty(), "No debe permitir ver simulaciones de otros usuarios");
    }
}
