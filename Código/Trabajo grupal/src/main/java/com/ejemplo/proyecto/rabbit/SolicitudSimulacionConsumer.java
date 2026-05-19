package com.ejemplo.proyecto.rabbit;

import com.ejemplo.proyecto.persistence.SolicitudSimulacionProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SolicitudSimulacionConsumer {
    private final SolicitudSimulacionProcessor processor;

    public SolicitudSimulacionConsumer(SolicitudSimulacionProcessor processor) {
        this.processor = processor;
    }

    @RabbitListener(queues = "${rabbitmq.queue.solicitudes}")
    public void recibir(SolicitudSimulacionMessage message) {
        this.processor.procesar(message.nombreUsuario(), message.token());
    }
}
