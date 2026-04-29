package com.ejemplo.proyecto.Logica;

import com.ejemplo.proyecto.domain.EspecificacionEntidad;
import com.ejemplo.proyecto.domain.ProcesoSimulacion;
import com.ejemplo.proyecto.domain.SolicitudSimulacion;
import com.ejemplo.proyecto.domain.Tablero;
import com.ejemplo.proyecto.domain.TipoEntidad;
import com.ejemplo.proyecto.persistence.ProcesoSimulacionRepository;
import com.ejemplo.proyecto.persistence.SimulacionPrinter;
import com.ejemplo.proyecto.persistence.SimulacionServiceFactory;
import com.ejemplo.proyecto.persistence.SolicitudSimulacionService;
import com.ejemplo.proyecto.persistence.TokenService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServicioSolicitudesSimulacion implements SolicitudSimulacionService {
    private static final int DEFAULT_LADO = 12;
    private static final int DEFAULT_PASOS = 3;

    private final GestorToken gestorToken;
    private final TableroFactory tableroFactory;
    private final SimulacionPrinter printer;
    private final SimulacionServiceFactory simulacionServiceFactory;
    private final ProcesoSimulacionRepository repository;
    private final TokenService tokenService;

    public ServicioSolicitudesSimulacion(
            GestorToken gestorToken,
            TableroFactory tableroFactory,
            SimulacionPrinter printer,
            SimulacionServiceFactory simulacionServiceFactory,
            ProcesoSimulacionRepository repository,
            TokenService tokenService
    ) {
        this.gestorToken = gestorToken;
        this.tableroFactory = tableroFactory;
        this.printer = printer;
        this.simulacionServiceFactory = simulacionServiceFactory;
        this.repository = repository;
        this.tokenService = tokenService;
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

        long semilla = this.gestorToken.generarSemilla(nombreUsuario.trim() + ":" + token);
        Tablero tablero = this.tableroFactory.crear(solicitud, semilla);
        this.simulacionServiceFactory.crear(semilla).ejecutarSimulacion(tablero, solicitud.getPasos());
        proceso.completar(tablero, this.printer.print(tablero));
        this.repository.guardar(proceso);
        return proceso;
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

    private void validarNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
    }
}
