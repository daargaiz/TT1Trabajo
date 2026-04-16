package com.ejemplo.proyecto.persistence;

import org.junit.jupiter.api.Test;

import com.ejemplo.proyecto.domain.*;

import static org.junit.jupiter.api.Assertions.*;

public class SimulacionPrinterTest {
	@Test
    void testPrintFormatoTablero() {
        // Set up
        Tablero tablero = new Tablero(10, 10);
        tablero.getEntidades().add(new EntidadQuieta(0, 0));
        

        SimulacionPrinter printer = new SimulacionPrinterStub();
        String resultado = printer.print(tablero);

        String esperado = "10,10\n0,0,0,quieta";
        assertEquals(esperado, resultado.trim(), "El formato de salida no es el esperado");
    }
}
