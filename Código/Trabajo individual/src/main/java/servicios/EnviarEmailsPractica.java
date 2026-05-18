package servicios;

import com.tt1.trabajo.utilidades.ServicioConsumibleClient;

import interfaces.InterfazEnviarEmails;
import modelo.Destinatario;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmailsPractica implements InterfazEnviarEmails {

    private final ServicioConsumibleClient client;
    private final Logger logger;

    public EnviarEmailsPractica(ServicioConsumibleClient client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    @Override
    public boolean enviarEmail(Destinatario dest, String email) {
        logger.info("Enviando email a {}", email);
        return client.enviarEmail(email, "");
    }
}
