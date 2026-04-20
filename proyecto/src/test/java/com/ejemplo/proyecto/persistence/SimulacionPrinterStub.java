package com.ejemplo.proyecto.persistence;

import com.ejemplo.proyecto.Logica.Printer;
import com.ejemplo.proyecto.domain.Tablero;

public class SimulacionPrinterStub implements SimulacionPrinter {
    private final Printer delegate = new Printer();
    
    @Override
    public String print(Tablero tablero) {
		return this.delegate.print(tablero);
    }
}
