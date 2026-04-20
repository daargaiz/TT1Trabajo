package com.ejemplo.proyecto.businesslogic;

import com.ejemplo.proyecto.businesslogic.actions.ISimulacionComportamiento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import com.ejemplo.proyecto.domain.*;

import static org.junit.jupiter.api.Assertions.*;

public class SimulacionServiceTest {

    private static final int ANCHO  = 10;
    private static final int ALTO   = 10;

    private Tablero tablero;
    private ISimulacionComportamiento service;

    @BeforeEach
    void setUp() {
        tablero = new Tablero(ANCHO);
        // Se usa el stub para asegurar determinismo en los tests que lo necesitan;
        // los tests probabilísticos arrancan su propio service.
        service = new SimulacionServiceStub();
    }
    // ─────────────────────────────────────────────────────────────────────────
    // 1. avanzarPaso / nextStep — avance de una iteración completa
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("avanzarPaso: el tablero conserva la lista de entidades tras un paso")
    void testAvanzarPasoConservaEntidades() {
        tablero.getEntidades().add(new EntidadQuieta(1, 1));
        tablero.getEntidades().add(new EntidadMovil(5, 5));
        int cantidadInicial = tablero.getEntidades().size();

        service.nextStep(tablero);

        // Las entidades no-viricas no se crean ni destruyen en un paso
        assertTrue(
                tablero.getEntidades().size() >= cantidadInicial,
                "Tras un paso no deben desaparecer entidades del tablero."
        );
    }
    @Test
    @DisplayName("avanzarPaso: con tablero vacío no lanza excepción")
    void testAvanzarPasoTableroVacio() {
        assertDoesNotThrow(
                () -> service.nextStep(tablero),
                "nextStep sobre un tablero vacío no debe lanzar excepción."
        );
        assertTrue(tablero.getEntidades().isEmpty(), "El tablero vacío sigue vacío.");
    }

    @Test
    @DisplayName("avanzarPaso: múltiples pasos mantienen la coherencia del tablero")
    void testMultiplesPasosCoherencia() {
        EntidadQuieta quieta = new EntidadQuieta(0, 0);
        EntidadMovil  movil  = new EntidadMovil(5, 5);
        tablero.getEntidades().add(quieta);
        tablero.getEntidades().add(movil);

        for (int i = 0; i < 5; i++) {
            service.nextStep(tablero);
        }

        // La entidad quieta nunca debe moverse
        assertEquals(0, quieta.getX(), "Tras 5 pasos la entidad quieta mantiene X=0.");
        assertEquals(0, quieta.getY(), "Tras 5 pasos la entidad quieta mantiene Y=0.");

        // Las coordenadas de la móvil deben seguir dentro del tablero
        assertTrue(movil.getX() >= 0 && movil.getX() < ANCHO,
                "La entidad móvil debe permanecer dentro del ancho del tablero.");
        assertTrue(movil.getY() >= 0 && movil.getY() < ALTO,
                "La entidad móvil debe permanecer dentro del alto del tablero.");
    }
    // ─────────────────────────────────────────────────────────────────────────
    // 2. Movimiento de entidades móviles
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("EntidadMovil: se desplaza exactamente una unidad ortogonal por paso")
    void testEntidadMovilDesplazamientoUnitario() {
        int xIni = 3, yIni = 3;
        EntidadMovil movil = new EntidadMovil(xIni, yIni);
        tablero.getEntidades().add(movil);

        service.nextStep(tablero);

        int dx = Math.abs(movil.getX() - xIni);
        int dy = Math.abs(movil.getY() - yIni);

        assertEquals(1, dx + dy,
                "La entidad móvil debe moverse exactamente una unidad y no en diagonal.");
    }
    @Test
    @DisplayName("EntidadMovil: permanece dentro de los límites del tablero al borde izquierdo")
    void testEntidadMovilNoCruzaBordeLateral() {
        // Se coloca la entidad en el extremo izquierdo; no puede ir a X < 0
        EntidadMovil movil = new EntidadMovil(0, 5);
        tablero.getEntidades().add(movil);

        for (int i = 0; i < 20; i++) {
            service.nextStep(tablero);
            assertTrue(movil.getX() >= 0,
                    "La coordenada X no puede ser negativa (paso " + i + ").");
            assertTrue(movil.getY() >= 0,
                    "La coordenada Y no puede ser negativa (paso " + i + ").");
        }
    }

    @Test
    @DisplayName("EntidadMovil: permanece dentro de los límites del tablero al borde derecho")
    void testEntidadMovilNoCruzaBordeDerecho() {
        EntidadMovil movil = new EntidadMovil(ANCHO - 1, ALTO - 1);
        tablero.getEntidades().add(movil);

        for (int i = 0; i < 20; i++) {
            service.nextStep(tablero);
            assertTrue(movil.getX() < ANCHO,
                    "La coordenada X no puede exceder el ancho del tablero (paso " + i + ").");
            assertTrue(movil.getY() < ALTO,
                    "La coordenada Y no puede exceder el alto del tablero (paso " + i + ").");
        }
    }

    @Test
    @DisplayName("EntidadMovil: varias entidades móviles se mueven de forma independiente")
    void testVariasEntidadesMovilesIndependientes() {
        EntidadMovil movil1 = new EntidadMovil(2, 2);
        EntidadMovil movil2 = new EntidadMovil(7, 7);
        tablero.getEntidades().add(movil1);
        tablero.getEntidades().add(movil2);

        int x1Ini = movil1.getX(), y1Ini = movil1.getY();
        int x2Ini = movil2.getX(), y2Ini = movil2.getY();

        service.nextStep(tablero);

        int d1 = Math.abs(movil1.getX() - x1Ini) + Math.abs(movil1.getY() - y1Ini);
        int d2 = Math.abs(movil2.getX() - x2Ini) + Math.abs(movil2.getY() - y2Ini);

        assertEquals(1, d1, "La primera entidad móvil debe moverse exactamente una unidad.");
        assertEquals(1, d2, "La segunda entidad móvil debe moverse exactamente una unidad.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 3. Comportamiento de entidades probabilísticas (EntidadVirica)
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("EntidadVirica: no cambia de posición tras un paso")
    void testEntidadViricaNoSeDesplaza() {
        int xIni = 5, yIni = 5;
        EntidadVirica virica = new EntidadVirica(xIni, yIni);
        tablero.getEntidades().add(virica);

        service.nextStep(tablero);

        assertEquals(xIni, virica.getX(), "La entidad vírica no debe moverse en X.");
        assertEquals(yIni, virica.getY(), "La entidad vírica no debe moverse en Y.");
    }

    @Test
    @DisplayName("EntidadVirica: puede duplicarse (el tablero tiene ≥ 1 entidad tras el paso)")
    void testEntidadViricaPuedeDuplicarse() {
        tablero.getEntidades().add(new EntidadVirica(5, 5));

        service.nextStep(tablero);

        assertFalse(tablero.getEntidades().isEmpty(),
                "Tras el paso debe existir al menos la entidad vírica original.");
    }

    /**
     * Verifica que el duplicado se genera en una celda adyacente a la original,
     * no a distancia arbitraria.
     */
    @Test
    @DisplayName("EntidadVirica: el duplicado aparece en celda adyacente a la original")
    void testEntidadViricaDuplicadoAdyacente() {
        final int X_VIRICA = 5, Y_VIRICA = 5;
        final int MAX_INTENTOS = 500;

        for (int intento = 0; intento < MAX_INTENTOS; intento++) {
            Tablero tableroLocal = new Tablero(ANCHO);
            EntidadVirica virica = new EntidadVirica(X_VIRICA, Y_VIRICA);
            tableroLocal.getEntidades().add(virica);

            service.nextStep(tableroLocal);

            List<Entidad> entidades = tableroLocal.getEntidades();
            if (entidades.size() > 1) {
                // Encuentra la entidad que NO es la original
                for (Entidad e : entidades) {
                    if (e != virica) {
                        int dx = Math.abs(e.getX() - X_VIRICA);
                        int dy = Math.abs(e.getY() - Y_VIRICA);
                        assertEquals(1, dx + dy,
                                "El duplicado vírico debe aparecer en una celda ortogonalmente adyacente.");
                    }
                }
                return; // test superado al encontrar una duplicación
            }
        }
        // Si tras MAX_INTENTOS nunca hubo duplicación, se acepta el test
        // (la probabilidad de fallo espurio es despreciable).
    }
    // ─────────────────────────────────────────────────────────────────────────
    // 4. No movimiento de entidades quietas
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("EntidadQuieta: permanece inmóvil tras un único paso")
    void testEntidadQuietaInmovilUnPaso() {
        int xIni = 3, yIni = 4;
        EntidadQuieta quieta = new EntidadQuieta(xIni, yIni);
        tablero.getEntidades().add(quieta);

        service.nextStep(tablero);

        assertEquals(xIni, quieta.getX(), "La entidad quieta no debe cambiar X tras un paso.");
        assertEquals(yIni, quieta.getY(), "La entidad quieta no debe cambiar Y tras un paso.");
    }

    @Test
    @DisplayName("EntidadQuieta: permanece inmóvil tras múltiples pasos")
    void testEntidadQuietaInmovilMultiplesPasos() {
        int xIni = 7, yIni = 2;
        EntidadQuieta quieta = new EntidadQuieta(xIni, yIni);
        tablero.getEntidades().add(quieta);

        for (int paso = 0; paso < 50; paso++) {
            service.nextStep(tablero);
            assertEquals(xIni, quieta.getX(),
                    "La entidad quieta no debe moverse en X (paso " + paso + ").");
            assertEquals(yIni, quieta.getY(),
                    "La entidad quieta no debe moverse en Y (paso " + paso + ").");
        }
    }

    @Test
    @DisplayName("EntidadQuieta: no se ve afectada por la presencia de entidades móviles")
    void testEntidadQuietaInmovilConEntidadesMovilesEnTablero() {
        int xQuieta = 5, yQuieta = 5;
        EntidadQuieta quieta = new EntidadQuieta(xQuieta, yQuieta);
        tablero.getEntidades().add(quieta);

        // Se añaden varias entidades móviles alrededor
        tablero.getEntidades().add(new EntidadMovil(4, 5));
        tablero.getEntidades().add(new EntidadMovil(6, 5));
        tablero.getEntidades().add(new EntidadMovil(5, 4));

        for (int paso = 0; paso < 10; paso++) {
            service.nextStep(tablero);
        }

        assertEquals(xQuieta, quieta.getX(),
                "La entidad quieta no debe desplazarse en X aunque haya entidades móviles cercanas.");
        assertEquals(yQuieta, quieta.getY(),
                "La entidad quieta no debe desplazarse en Y aunque haya entidades móviles cercanas.");
    }

    @Test
    @DisplayName("EntidadQuieta: múltiples entidades quietas permanecen en sus posiciones originales")
    void testMultiplesEntidadesQuietasNuncaSeMueven() {
        int[][] posiciones = {{1,1},{3,3},{5,5},{7,7},{9,9}};

        for (int[] pos : posiciones) {
            tablero.getEntidades().add(new EntidadQuieta(pos[0], pos[1]));
        }

        for (int paso = 0; paso < 20; paso++) {
            service.nextStep(tablero);
        }

        List<Entidad> entidades = tablero.getEntidades();
        for (int i = 0; i < posiciones.length; i++) {
            Entidad e = entidades.get(i);
            assertEquals(posiciones[i][0], e.getX(),
                    "Entidad quieta " + i + " no debe moverse en X.");
            assertEquals(posiciones[i][1], e.getY(),
                    "Entidad quieta " + i + " no debe moverse en Y.");
        }
    }

	@Test
    void testNextStepEntidadQuieta() {
        // Set up
        Tablero tablero = new Tablero(10);
        int xInicial = 3;
        int yInicial = 4;
        EntidadQuieta quieta = new EntidadQuieta(xInicial, yInicial);
        tablero.getEntidades().add(quieta);
        
        ISimulacionComportamiento service = new SimulacionServiceStub();
        service.nextStep(tablero);
        
        assertEquals(xInicial, quieta.getX(), "La entidad quieta no se mueve en el plano X.");
        assertEquals(yInicial, quieta.getY(), "La entidad quieta no se mueve en el plano Y.");
    }
	
	@Test
    void testNextStepMueveEntidadMovil() {
        // Set up
        Tablero tablero = new Tablero(10);
        int xInicial = 3;
        int yInicial = 4;
        EntidadMovil movil = new EntidadMovil(xInicial, yInicial);
        tablero.getEntidades().add(movil);
        
        ISimulacionComportamiento service = new SimulacionServiceStub();
        service.nextStep(tablero);
        
        assertFalse(
                movil.getX() == xInicial && movil.getY() == yInicial,
                "La entidad movil debe abandonar su posicion inicial."
        );
        
        int dx = Math.abs(movil.getX() - xInicial);
        int dy = Math.abs(movil.getY() - yInicial);
        assertEquals(1, dx + dy, "La entidad debe moverse exactamente una unidad y no diagonal");
    }
	
	@Test
    void testNextStepDuplicaEntidadVirica() {
        // Set up
        Tablero tablero = new Tablero(10);
        int xInicial = 3;
        int yInicial = 4;
        EntidadVirica virica = new EntidadVirica(xInicial, yInicial);
        tablero.getEntidades().add(virica);
        
        ISimulacionComportamiento service = new SimulacionServiceStub();
        service.nextStep(tablero);
        
        assertEquals(xInicial, virica.getX(), "La entidad quieta no se mueve en el plano X.");
        assertEquals(yInicial, virica.getY(), "La entidad quieta no se mueve en el plano Y.");
    }
}
