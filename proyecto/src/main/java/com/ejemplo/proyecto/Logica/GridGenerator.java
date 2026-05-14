package com.ejemplo.proyecto.Logica;

import com.ejemplo.proyecto.domain.EspecificacionEntidad;
import com.ejemplo.proyecto.domain.SolicitudSimulacion;
import com.ejemplo.proyecto.domain.Tablero;
import com.ejemplo.proyecto.domain.TipoEntidad;
import com.ejemplo.proyecto.persistence.GridDataService;
import com.ejemplo.proyecto.persistence.SimulacionPrinter;
import com.ejemplo.proyecto.persistence.SimulacionServiceFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Genera tableros y salidas de texto para la API de grid de demostración.
 */
@Service
public class GridGenerator implements GridDataService {
    private static final int DEFAULT_LADO = 12;
    // Con el estado inicial (t=0), DEFAULT_PASOS=18 produce 19 "etapas" (t=0..18).
    private static final int DEFAULT_PASOS = 18;

    private final GestorToken gestorToken;
    private final TableroFactory tableroFactory;
    private final SimulacionPrinter printer;
    private final SimulacionServiceFactory simulacionServiceFactory;

    public GridGenerator(
            GestorToken gestorToken,
            TableroFactory tableroFactory,
            SimulacionPrinter printer,
            SimulacionServiceFactory simulacionServiceFactory
    ) {
        this.gestorToken = gestorToken;
        this.tableroFactory = tableroFactory;
        this.printer = printer;
        this.simulacionServiceFactory = simulacionServiceFactory;
    }

    /**
     * Genera el texto de la simulación usando el número de pasos por defecto.
     * @param token Token usado como base de la semilla.
     * @return Representación textual del tablero simulado.
     */
    @Override
    public String generarTexto(String token) {
        return generarTexto(token, DEFAULT_PASOS);
    }

    /**
     * Genera el texto de la simulación con el número de pasos indicado.
     * @param token Token usado como base de la semilla.
     * @param pasos Número de pasos a ejecutar.
     * @return Representación textual del tablero simulado.
     */
    @Override
    public String generarTexto(String token, int pasos) {
        return this.printer.print(generarTablero(token, pasos));
    }

    /**
     * Genera un tablero usando el número de pasos por defecto.
     * @param token Token usado como base de la semilla.
     * @return Tablero simulado.
     */
    @Override
    public Tablero generarTablero(String token) {
        return generarTablero(token, DEFAULT_PASOS);
    }

    /**
     * Genera un tablero determinista a partir del token recibido.
     * @param token Token usado como base de la semilla.
     * @param pasos Número de pasos a ejecutar.
     * @return Tablero con su historial calculado.
     */
    @Override
    public Tablero generarTablero(String token, int pasos) {
        if (pasos < 0) {
            throw new IllegalArgumentException("El numero de pasos no puede ser negativo");
        }

        long semilla = this.gestorToken.generarSemilla(token);
        SolicitudSimulacion solicitud = new SolicitudSimulacion(
                0,
                "demo",
                DEFAULT_LADO,
                pasos,
                List.of(
                        new EspecificacionEntidad(TipoEntidad.QUIETA, 1),
                        new EspecificacionEntidad(TipoEntidad.MOVIL, 2),
                        new EspecificacionEntidad(TipoEntidad.VIRICA, 1)
                )
        );
        Tablero tablero = this.tableroFactory.crear(solicitud, semilla);
        this.simulacionServiceFactory.crear(semilla).ejecutarSimulacion(tablero, pasos);
        return tablero;
    }
}
