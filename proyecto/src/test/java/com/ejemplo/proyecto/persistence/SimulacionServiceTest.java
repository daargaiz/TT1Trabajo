package com.ejemplo.proyecto.persistence;

import org.junit.jupiter.api.Test;

import com.ejemplo.proyecto.domain.*;

import static org.junit.jupiter.api.Assertions.*;

public class SimulacionServiceTest {
	@Test
    void testNextStepEntidadQuieta() {
        // Set up
        Tablero tablero = new Tablero(10, 10);  
        int xInicial = 3;
        int yInicial = 4;
        EntidadQuieta quieta = new EntidadQuieta(xInicial, yInicial);
        tablero.getEntidades().add(quieta);
        
        SimulacionService service = new SimulacionServiceStub();
        service.nextStep(tablero);
        
        assertEquals(xInicial, quieta.getX(), "La entidad quieta no se mueve en el plano X.");
        assertEquals(yInicial, quieta.getY(), "La entidad quieta no se mueve en el plano Y.");
    }
	
	@Test
    void testNextStepMueveEntidadMovil() {
        // Set up
        Tablero tablero = new Tablero(10, 10);
        int xInicial = 3;
        int yInicial = 4;
        EntidadMovil movil = new EntidadMovil(xInicial, yInicial);
        tablero.getEntidades().add(movil);
        
        SimulacionService service = new SimulacionServiceStub();
        service.nextStep(tablero);
        
        assertNotEquals(xInicial, movil.getX(), "La entidad movil se mueve en el plano X.");
        assertEquals(yInicial, movil.getY(), "La entidad movil se mueve en el plano Y.");
        
        int dx = Math.abs(movil.getX() - xInicial);
        int dy = Math.abs(movil.getY() - yInicial);
        assertEquals(1, dx + dy, "La entidad debe moverse exactamente una unidad y no diagonal");
    }
	
	@Test
    void testNextStepDuplicaEntidadVirica() {
        // Set up
        Tablero tablero = new Tablero(10, 10);
        int xInicial = 3;
        int yInicial = 4;
        EntidadVirica virica = new EntidadVirica(xInicial, yInicial);
        tablero.getEntidades().add(virica);
        
        SimulacionService service = new SimulacionServiceStub();
        service.nextStep(tablero);
        
        assertEquals(xInicial, virica.getX(), "La entidad quieta no se mueve en el plano X.");
        assertEquals(yInicial, virica.getY(), "La entidad quieta no se mueve en el plano Y.");
    }
}
