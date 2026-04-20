package com.ejemplo.proyecto.businesslogic;

import com.ejemplo.proyecto.Logica.Printer;
import com.ejemplo.proyecto.businesslogic.printer.SimulacionPrinter;
import com.ejemplo.proyecto.domain.Tablero;

public class SimulacionPrinterStub implements SimulacionPrinter {
    private final Printer delegate = new Printer();
    
    @Override
    public String print(Tablero tablero) {
		return this.delegate.print(tablero);
    }
}
