package com.ejemplo.proyecto.Logica;

import com.ejemplo.proyecto.domain.EspecificacionEntidad;
import com.ejemplo.proyecto.domain.ProcesoSimulacion;
import com.ejemplo.proyecto.domain.SolicitudSimulacion;
import com.ejemplo.proyecto.domain.Tablero;
import com.ejemplo.proyecto.domain.TipoEntidad;
import com.ejemplo.proyecto.persistence.ProcesoSimulacionRepository;
import com.ejemplo.proyecto.persistence.SimulacionPrinter;
import com.ejemplo.proyecto.persistence.SimulacionServiceFactory;
import com.ejemplo.proyecto.persistence.SolicitudSimulacionProcessor;
import com.ejemplo.proyecto.persistence.SolicitudSimulacionService;
import com.ejemplo.proyecto.persistence.SolicitudSimulacionPublisher;
import com.ejemplo.proyecto.persistence.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Coordina la creación, ejecución y consulta de solicitudes de simulación.
 */
@Service
public class ServicioSolicitudesSimulacion implements SolicitudSimulacionService, SolicitudSimulacionProcessor {
    private static final int DEFAULT_LADO = 12;
    // Con el estado inicial (t=0), DEFAULT_PASOS=18 produce 19 "etapas" (t=0..18).
    private static final int DEFAULT_PASOS = 18;

    private final GestorToken gestorToken;
    private final TableroFactory tableroFactory;
    private final SimulacionPrinter printer;
    private final SimulacionServiceFactory simulacionServiceFactory;
    private final ProcesoSimulacionRepository repository;
    private final TokenService tokenService;
    private final SolicitudSimulacionPublisher solicitudSimulacionPublisher;
    private final boolean procesamientoSincrono;

    @Autowired
    public ServicioSolicitudesSimulacion(
            GestorToken gestorToken,
            TableroFactory tableroFactory,
            SimulacionPrinter printer,
            SimulacionServiceFactory simulacionServiceFactory,
            ProcesoSimulacionRepository repository,
            TokenService tokenService
    ) {
        this(
                gestorToken,
                tableroFactory,
                printer,
                simulacionServiceFactory,
                repository,
                tokenService,
                (nombreUsuario, token) -> {
                },
                true
        );
    }

    public ServicioSolicitudesSimulacion(
            GestorToken gestorToken,
            TableroFactory tableroFactory,
            SimulacionPrinter printer,
            SimulacionServiceFactory simulacionServiceFactory,
            ProcesoSimulacionRepository repository,
            TokenService tokenService,
            SolicitudSimulacionPublisher solicitudSimulacionPublisher
    ) {
        this(
                gestorToken,
                tableroFactory,
                printer,
                simulacionServiceFactory,
                repository,
                tokenService,
                solicitudSimulacionPublisher,
                !solicitudSimulacionPublisher.isAsincrono()
        );
    }

    private ServicioSolicitudesSimulacion(
            GestorToken gestorToken,
            TableroFactory tableroFactory,
            SimulacionPrinter printer,
            SimulacionServiceFactory simulacionServiceFactory,
            ProcesoSimulacionRepository repository,
            TokenService tokenService,
            SolicitudSimulacionPublisher solicitudSimulacionPublisher,
            boolean procesamientoSincrono
    ) {
        this.gestorToken = gestorToken;
        this.tableroFactory = tableroFactory;
        this.printer = printer;
        this.simulacionServiceFactory = simulacionServiceFactory;
        this.repository = repository;
        this.tokenService = tokenService;
        this.solicitudSimulacionPublisher = solicitudSimulacionPublisher;
        this.procesamientoSincrono = procesamientoSincrono;
    }

    @Override
    public ProcesoSimulacion solicitar(
            String nombreUsuario,
            List<String> nombresEntidades,
            List<Integer> cantidadesIniciales
    ) {
        validarNombreUsuario(nombreUsuario);
        List<EspecificacionEntidad> especificaciones = normalizarSolicitud(nombresEntidades, cantidadesIniciales);
        int token = this.tokenService.generarToken();
        SolicitudSimulacion solicitud = new SolicitudSimulacion(
                token,
                nombreUsuario,
                DEFAULT_LADO,
                DEFAULT_PASOS,
                especificaciones
        );

        ProcesoSimulacion proceso = new ProcesoSimulacion(solicitud);
        this.repository.guardar(proceso);
        this.solicitudSimulacionPublisher.publicar(solicitud.getNombreUsuario(), token);
        if (this.procesamientoSincrono) {
            procesar(solicitud.getNombreUsuario(), token);
        }
        return proceso;
    }

    @Override
    public void procesar(String nombreUsuario, int token) {
        validarNombreUsuario(nombreUsuario);
        if (token < 0) {
            throw new IllegalArgumentException("El token no puede ser negativo");
        }

        ProcesoSimulacion proceso = this.repository.buscarPorUsuarioYToken(nombreUsuario, token)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
        if (proceso.isDone()) {
            return;
        }

        SolicitudSimulacion solicitud = proceso.getSolicitud();
        long semilla = this.gestorToken.generarSemilla(solicitud.getNombreUsuario().trim() + ":" + token);
        Tablero tablero = this.tableroFactory.crear(solicitud, semilla);
        this.simulacionServiceFactory.crear(semilla).ejecutarSimulacion(tablero, solicitud.getPasos());
        proceso.completar(tablero, this.printer.print(tablero));
        this.repository.guardar(proceso);
    }

    @Override
    public Optional<ProcesoSimulacion> consultar(String nombreUsuario, int token) {
        validarNombreUsuario(nombreUsuario);
        if (token < 0) {
            throw new IllegalArgumentException("El token no puede ser negativo");
        }
        return this.repository.buscarPorUsuarioYToken(nombreUsuario, token);
    }

    @Override
    public List<Integer> listarTokensUsuario(String nombreUsuario) {
        validarNombreUsuario(nombreUsuario);
        return this.repository.buscarTokensPorUsuario(nombreUsuario);
    }

    @Override
    public boolean comprobarSolicitud(String nombreUsuario, int token) {
        validarNombreUsuario(nombreUsuario);
        if (token < 0) {
            throw new IllegalArgumentException("El token no puede ser negativo");
        }
        return consultar(nombreUsuario, token)
                .map(ProcesoSimulacion::isDone)
                .orElse(false);
    }

    /**
     * Normaliza los nombres y cantidades recibidos desde la API.
     * Agrupa cantidades repetidas por tipo de entidad y descarta entradas con cantidad cero.
     */
    private List<EspecificacionEntidad> normalizarSolicitud(
            List<String> nombresEntidades,
            List<Integer> cantidadesIniciales
    ) {
        if (nombresEntidades == null || cantidadesIniciales == null) {
            throw new IllegalArgumentException("Los nombres de entidad y las cantidades son obligatorios");
        }
        if (nombresEntidades.size() != cantidadesIniciales.size()) {
            throw new IllegalArgumentException("Cada nombre de entidad debe tener una cantidad asociada");
        }

        Map<TipoEntidad, Integer> cantidadesPorTipo = new LinkedHashMap<>();
        for (int i = 0; i < nombresEntidades.size(); i++) {
            Integer cantidad = cantidadesIniciales.get(i);
            if (cantidad == null) {
                throw new IllegalArgumentException("Las cantidades iniciales no pueden ser nulas");
            }
            if (cantidad < 0) {
                throw new IllegalArgumentException("Las cantidades iniciales no pueden ser negativas");
            }
            if (cantidad == 0) {
                continue;
            }

            TipoEntidad tipo = TipoEntidad.desdeNombre(nombresEntidades.get(i));
            cantidadesPorTipo.merge(tipo, cantidad, Integer::sum);
        }

        if (cantidadesPorTipo.isEmpty()) {
            throw new IllegalArgumentException("La solicitud debe contener al menos una entidad");
        }

        List<EspecificacionEntidad> especificaciones = new ArrayList<>();
        cantidadesPorTipo.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().name()))
                .forEach(entry -> especificaciones.add(new EspecificacionEntidad(entry.getKey(), entry.getValue())));

        return especificaciones;
    }

    /**
     * Valida que el usuario recibido pueda usarse para crear o consultar solicitudes.
     */
    private void validarNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
    }
}
