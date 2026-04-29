package com.ejemplo.proyecto.Logica;

import com.ejemplo.proyecto.domain.EstadoEntidad;
import com.ejemplo.proyecto.domain.Tablero;
import com.ejemplo.proyecto.persistence.SimulacionPrinter;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class Printer implements SimulacionPrinter {

    @Override
    public String print(Tablero tablero) {
        tablero.registrarEstadoInicialSiNecesario();

        StringBuilder builder = new StringBuilder();
        builder.append(tablero.getLado());

        tablero.getHistorial().stream()
                .sorted(Comparator
                        .comparingInt(EstadoEntidad::tiempo)
                        .thenComparingInt(EstadoEntidad::y)
                        .thenComparingInt(EstadoEntidad::x)
                        .thenComparing(EstadoEntidad::color))
                .forEach(estado -> builder.append("\n")
                        .append(estado.tiempo())
                        .append(",")
                        .append(estado.y())
                        .append(",")
                        .append(estado.x())
                        .append(",")
                        .append(estado.color()));

        return builder.toString();
    }
}
