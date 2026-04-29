package com.ejemplo.proyecto.persistence;

import com.ejemplo.proyecto.domain.Tablero;

public interface GridDataService {
    String generarTexto(String token);
    String generarTexto(String token, int pasos);
    Tablero generarTablero(String token);
    Tablero generarTablero(String token, int pasos);
}
