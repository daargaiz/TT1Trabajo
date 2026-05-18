package servicios;

import com.tt1.trabajo.utilidades.ServicioConsumibleClient;

import interfaces.InterfazContactoSim;
import modelo.DatosSimulation;
import modelo.DatosSolicitud;
import modelo.Entidad;
import modelo.Punto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioPractica implements InterfazContactoSim {

    private final ServicioConsumibleClient client;
    private final List<Entidad> entidades;

    public ServicioPractica(ServicioConsumibleClient client) {
        this.client = client;
        this.entidades = new ArrayList<>();

        Entidad e1 = new Entidad();
        e1.setId(1);
        e1.setName("Prueba1");
        e1.setDescripcion("Primer elemento de la lista");

        Entidad e2 = new Entidad();
        e2.setId(2);
        e2.setName("Prueba2");
        e2.setDescripcion("Segundo elemento de la lista");

        Entidad e3 = new Entidad();
        e3.setId(3);
        e3.setName("Prueba3");
        e3.setDescripcion("Tercer y ultimo elemento de la lista");

        entidades.add(e1);
        entidades.add(e2);
        entidades.add(e3);
    }

    @Override
    public int solicitarSimulation(DatosSolicitud sol) {
        List<String> nombresEntidades = new ArrayList<>();
        List<Integer> cantidadesIniciales = new ArrayList<>();

        for (Entidad entidad : entidades) {
            Integer cantidad = sol.getNums().get(entidad.getId());
            if (cantidad != null) {
                nombresEntidades.add(entidad.getName());
                cantidadesIniciales.add(cantidad);
            }
        }

        return client.solicitarSimulation(nombresEntidades, cantidadesIniciales);
    }

    @Override
    public DatosSimulation descargarDatos(int ticket) {
        return parsearRespuestaGrid(client.descargarResultados(ticket));
    }

    @Override
    public List<Entidad> getEntities() {
        return entidades;
    }

    @Override
    public boolean isValidEntityId(int id) {
        return entidades.stream().anyMatch(entidad -> entidad.getId() == id);
    }

    private DatosSimulation parsearRespuestaGrid(String respuesta) {
        DatosSimulation datos = new DatosSimulation();
        Map<Integer, List<Punto>> puntosPorTiempo = new HashMap<>();

        datos.setAnchoTablero(0);
        datos.setMaxSegundos(1);
        datos.setPuntos(puntosPorTiempo);
        puntosPorTiempo.put(0, new ArrayList<>());

        if (respuesta == null || respuesta.isBlank()) {
            return datos;
        }

        String[] lineas = respuesta.strip().split("\\R+");
        try {
            datos.setAnchoTablero(Integer.parseInt(lineas[0].trim()));
        } catch (NumberFormatException e) {
            return datos;
        }

        int maxTiempo = -1;

        for (int i = 1; i < lineas.length; i++) {
            String linea = lineas[i].trim();
            if (linea.isEmpty()) {
                continue;
            }

            String[] partes = linea.split(",");
            if (partes.length != 4) {
                continue;
            }

            try {
                int tiempo = Integer.parseInt(partes[0].trim());
                int y = Integer.parseInt(partes[1].trim());
                int x = Integer.parseInt(partes[2].trim());
                String color = partes[3].trim();

                Punto punto = new Punto();
                punto.setY(y);
                punto.setX(x);
                punto.setColor(color);

                puntosPorTiempo.computeIfAbsent(tiempo, ignored -> new ArrayList<>()).add(punto);
                maxTiempo = Math.max(maxTiempo, tiempo);
            } catch (NumberFormatException ignored) {
            }
        }

        if (maxTiempo >= 0) {
            for (int t = 0; t <= maxTiempo; t++) {
                puntosPorTiempo.putIfAbsent(t, new ArrayList<>());
            }
            datos.setMaxSegundos(maxTiempo + 1);
        }

        return datos;
    }
}
