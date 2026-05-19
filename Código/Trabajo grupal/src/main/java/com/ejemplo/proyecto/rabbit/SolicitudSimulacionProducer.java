package com.ejemplo.proyecto.rabbit;

import com.ejemplo.proyecto.persistence.SolicitudSimulacionPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SolicitudSimulacionProducer implements SolicitudSimulacionPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final String queueName;

    public SolicitudSimulacionProducer(
            RabbitTemplate rabbitTemplate,
            @Value("${rabbitmq.queue.solicitudes}") String queueName
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
    }

    public void enviar(SolicitudSimulacionMessage message) {
        this.rabbitTemplate.convertAndSend(this.queueName, message);
    }

    @Override
    public void publicar(String nombreUsuario, int token) {
        enviar(new SolicitudSimulacionMessage(nombreUsuario, token));
    }

    @Override
    public boolean isAsincrono() {
        return true;
    }
}
