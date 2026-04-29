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

@Service
public class GridGenerator implements GridDataService {
    private static final int DEFAULT_LADO = 12;
    private static final int DEFAULT_PASOS = 3;

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

    @Override
    public String generarTexto(String token) {
        return generarTexto(token, DEFAULT_PASOS);
    }

    @Override
    public String generarTexto(String token, int pasos) {
        return this.printer.print(generarTablero(token, pasos));
    }

    @Override
    public Tablero generarTablero(String token) {
        return generarTablero(token, DEFAULT_PASOS);
    }

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
