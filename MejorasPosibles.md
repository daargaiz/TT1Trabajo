## Mejoras futuras

Algunas mejoras que nos hubiera gustado implementar con más tiempo:

| # | Mejora | Descripción |
|---|--------|-------------|
| 1 |  **Tests exclusivos para RabbitMQ** | Suite de tests dedicada para validar el comportamiento del sistema de mensajería de forma aislada |
| 2 |  **Separar API y worker RabbitMQ** | Desacoplar el servicio API del worker consumidor para mejorar escalabilidad y mantenibilidad |
| 3 |  **Gestión de errores en RabbitMQ** | Manejo robusto de fallos: dead-letter queues, reintentos automáticos y alertas ante mensajes no procesados |
| 4 |  **Mejoras indicadas por el cliente** | Ej: añadir un sistema de tiempo de vida (TTL) y ciclos de alimentación configurables |
