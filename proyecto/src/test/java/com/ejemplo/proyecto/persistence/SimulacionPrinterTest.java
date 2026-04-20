package com.ejemplo.proyecto.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ejemplo.proyecto.domain.*;

import static org.junit.jupiter.api.Assertions.*;

public class SimulacionPrinterTest {
    private SimulacionPrinter printer;

    @BeforeEach
    void setUp() {
        printer = new SimulacionPrinterStub();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 1. Cabecera: dimensiones del tablero
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("print: la primera línea contiene el ancho y el alto del tablero")
    void testCabeceraContieneAnchoyAlto() {
        Tablero tablero = new Tablero(10);

        String resultado = printer.print(tablero);
        String primeraLinea = resultado.trim().split("\n")[0];

        assertEquals("10", primeraLinea,
                "La primera línea debe contener 'lado'.");
    }

    @Test
    @DisplayName("print: la cabecera refleja las dimensiones reales del tablero (5x8)")
    void testCabeceraOtrasDimensiones() {
        Tablero tablero = new Tablero(8);

        String resultado = printer.print(tablero);
        String primeraLinea = resultado.trim().split("\n")[0];

        assertEquals("8", primeraLinea,
                "La primera línea debe reflejar las dimensiones reales del tablero.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 2. Tablero vacío
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("print: tablero vacío solo genera la cabecera de dimensiones")
    void testTableroVacioSoloCabecera() {
        Tablero tablero = new Tablero(10);

        String resultado = printer.print(tablero).trim();
        String[] lineas = resultado.split("\n");

        assertEquals(1, lineas.length,
                "Un tablero vacío solo debe producir la línea de cabecera.");
        assertEquals("10", lineas[0]);
    }

    @Test
    @DisplayName("print: tablero vacío no lanza excepción")
    void testTableroVacioNoLanzaExcepcion() {
        Tablero tablero = new Tablero(10);

        assertDoesNotThrow(() -> printer.print(tablero),
                "print sobre un tablero vacío no debe lanzar excepción.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 3. Formato de cada línea de entidad: tiempo,y,x,tipo
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("print: una EntidadQuieta produce una línea con formato tiempo,y,x,quieta")
    void testFormatoLineaEntidadQuieta() {
        Tablero tablero = new Tablero(10);
        tablero.getEntidades().add(new EntidadQuieta(0, 0));

        String resultado = printer.print(tablero);
        String esperado = "10,10\n0,0,0,quieta";

        assertEquals(esperado, resultado.trim(),
                "El formato de salida para EntidadQuieta no es el esperado.");
    }

    @Test
    @DisplayName("print: una EntidadMovil produce una línea con formato tiempo,y,x,movil")
    void testFormatoLineaEntidadMovil() {
        Tablero tablero = new Tablero(10);
        tablero.getEntidades().add(new EntidadMovil(3, 4));

        String resultado = printer.print(tablero);
        String[] lineas = resultado.trim().split("\n");

        assertEquals(2, lineas.length,
                "Debe haber exactamente dos líneas: cabecera y la entidad.");
        assertEquals("0,4,3,movil", lineas[1],
                "El formato de salida para EntidadMovil no es el esperado (tiempo,y,x,movil).");
    }

    @Test
    @DisplayName("print: una EntidadVirica produce una línea con formato tiempo,y,x,virica")
    void testFormatoLineaEntidadVirica() {
        Tablero tablero = new Tablero(10);
        tablero.getEntidades().add(new EntidadVirica(7, 2));

        String resultado = printer.print(tablero);
        String[] lineas = resultado.trim().split("\n");

        assertEquals(2, lineas.length,
                "Debe haber exactamente dos líneas: cabecera y la entidad.");
        assertEquals("0,2,7,virica", lineas[1],
                "El formato de salida para EntidadVirica no es el esperado (tiempo,y,x,virica).");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 4. Posiciones: las coordenadas x e y se vuelcan correctamente
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("print: las coordenadas x e y se escriben en el orden correcto (y antes que x)")
    void testOrdenCoordenadasYX() {
        Tablero tablero = new Tablero(10);
        tablero.getEntidades().add(new EntidadQuieta(3, 7)); // x=3, y=7

        String resultado = printer.print(tablero);
        String lineaEntidad = resultado.trim().split("\n")[1];

        // Formato esperado: tiempo,y,x,tipo → 0,7,3,quieta
        assertEquals("0,7,3,quieta", lineaEntidad,
                "La coordenada y debe aparecer antes que x en la línea de salida.");
    }

    @Test
    @DisplayName("print: entidad en esquina (9,9) escribe coordenadas correctamente")
    void testEntidadEnEsquinaMaxima() {
        Tablero tablero = new Tablero(10);
        tablero.getEntidades().add(new EntidadQuieta(9, 9));

        String resultado = printer.print(tablero);
        String lineaEntidad = resultado.trim().split("\n")[1];

        assertEquals("0,9,9,quieta", lineaEntidad,
                "Las coordenadas en la esquina máxima deben escribirse correctamente.");
    }

    @Test
    @DisplayName("print: entidad en posición (0,0) escribe coordenadas correctamente")
    void testEntidadEnOrigen() {
        Tablero tablero = new Tablero(10);
        tablero.getEntidades().add(new EntidadMovil(0, 0));

        String resultado = printer.print(tablero);
        String lineaEntidad = resultado.trim().split("\n")[1];

        assertEquals("0,0,0,movil", lineaEntidad,
                "Las coordenadas en el origen deben escribirse correctamente.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 5. Múltiples entidades
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("print: múltiples entidades generan una línea por entidad además de la cabecera")
    void testMultiplesEntidadesGeneranLineasCorrectas() {
        Tablero tablero = new Tablero(10);
        tablero.getEntidades().add(new EntidadQuieta(1, 1));
        tablero.getEntidades().add(new EntidadMovil(5, 5));
        tablero.getEntidades().add(new EntidadVirica(8, 3));

        String resultado = printer.print(tablero);
        String[] lineas = resultado.trim().split("\n");

        assertEquals(4, lineas.length,
                "Deben generarse 4 líneas: 1 cabecera + 3 entidades.");
    }

    @Test
    @DisplayName("print: con tres entidades distintas aparecen los tres tipos en la salida")
    void testTresEntidadesTiposDistintos() {
        Tablero tablero = new Tablero(10);
        tablero.getEntidades().add(new EntidadQuieta(0, 0));
        tablero.getEntidades().add(new EntidadMovil(1, 1));
        tablero.getEntidades().add(new EntidadVirica(2, 2));

        String resultado = printer.print(tablero);

        assertTrue(resultado.contains("quieta"),
                "La salida debe contener la etiqueta 'quieta'.");
        assertTrue(resultado.contains("movil"),
                "La salida debe contener la etiqueta 'movil'.");
        assertTrue(resultado.contains("virica"),
                "La salida debe contener la etiqueta 'virica'.");
    }

    @Test
    @DisplayName("print: el número de líneas de entidades coincide con el tamaño de la lista")
    void testNumeroLineasCoincideConEntidades() {
        Tablero tablero = new Tablero(10);
        int numEntidades = 5;
        for (int i = 0; i < numEntidades; i++) {
            tablero.getEntidades().add(new EntidadQuieta(i, i));
        }

        String resultado = printer.print(tablero);
        // +1 por la línea de cabecera
        long lineasNoVacias = resultado.trim().lines()
                .filter(l -> !l.isBlank())
                .count();

        assertEquals(numEntidades + 1, lineasNoVacias,
                "El número de líneas debe ser igual al número de entidades más la cabecera.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 6. Robustez del formato (sin espacios extra, sin saltos dobles)
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("print: ninguna línea contiene espacios en blanco innecesarios")
    void testSinEspaciosInnecesarios() {
        Tablero tablero = new Tablero(10);
        tablero.getEntidades().add(new EntidadQuieta(3, 4));

        String resultado = printer.print(tablero);

        for (String linea : resultado.trim().split("\n")) {
            assertFalse(linea.contains(" "),
                    "Ninguna línea debe contener espacios en blanco: '" + linea + "'.");
        }
    }

    @Test
    @DisplayName("print: la salida no contiene líneas vacías entre entidades")
    void testSinLineasVacias() {
        Tablero tablero = new Tablero(10);
        tablero.getEntidades().add(new EntidadQuieta(1, 1));
        tablero.getEntidades().add(new EntidadMovil(2, 2));

        String resultado = printer.print(tablero);

        for (String linea : resultado.trim().split("\n")) {
            assertFalse(linea.isBlank(),
                    "No deben aparecer líneas vacías entre las entidades.");
        }
    }
	@Test
    void testPrintFormatoTablero() {
        // Set up
        Tablero tablero = new Tablero(10);
        tablero.getEntidades().add(new EntidadQuieta(0, 0));
        

        SimulacionPrinter printer = new SimulacionPrinterStub();
        String resultado = printer.print(tablero);

        String esperado = "10\n0,0,0,quieta";
        assertEquals(esperado, resultado.trim(), "El formato de salida no es el esperado");
    }
}
