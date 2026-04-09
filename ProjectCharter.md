# Carta de inicio de proyecto - Aplicación de servicio

| Campo | Valor |
|-------|-------|
| Nombre del proyecto| Aplicación de simulación de entidades en una cuadrícula.| 
|Sponsor del proyecto | Universidad de La Rioja, Facultad de Ciencia y Tecnología |
| Fecha | 09 de abril de 2026|
|Revisión |  1|
| Scrum Master|  Javier Ortega Villar|
| Desarrolladores| Daniel Argaiz García, Juan Pulgar y Víctor Esteban Chacobo| 
--- 

## Descripción general: 
Desarrollar una aplicación que simule el comportamiento de distintas entidades sobre una cuadrícula, permitiendo observar cómo se propagan o se mantienen los colores en función de reglas simples de movimiento. 
---

## Objetivo (SMART):
Construir una aplicación funcional, documentada y desplegable, capaz de: 
- representar una cuadrícula, 
- simular distintos tipos de entidades, 
- aplicar reglas de movimiento y permanencia, 
- y ofrecer un servicio consumible mediante API REST. 
La fecha límite del proyecto corresponde con el 21 de mayo de 2026, cerrando el proyecto en exactamente 6 semanas. 
---

## Alcance: 
El proyecto incluirá:
- aplicación funcional, 
- código fuente, 
- documentación técnica,
- manual de despliegue, 
- pruebas de la aplicación, 
- y despliegue del servicio en un servidor. 

Queda fuera del alcance, salvo acuerdo posterior: 
- interfaz gráfica avanzada, 
- autenticación de usuarios, 
- persistencia compleja, 
- escalado distribuido, 
- o funcionalidades no vinculadas directamente con la simulación. 
---

## Hitos principales: 
El proyecto se desarrolla durante 6 semanas, con 3 horas semanales por persona, para un total aproximado de 60 horas.

| ID | Hito | Descripción | Fecha |
|----|------|-------------|--------|
| H1 | Definición inicial | Definir enfoque técnico y alcance del proyecto | 09/04/2026 |
| H2 | Servicio base | Implementación inicial del servicio REST | 16/04/2026 |
| H3 | Pruebas | Desarrollo de pruebas unitarias y de integración | 23/04/2026 |
| H4 | Desarrollo completo | Implementación completa de la aplicación | 30/04/2026 |
| H5 | Despliegue | Despliegue del servicio en el servidor | 07/05/2026 |
| H6 | Validación final | Verificación del correcto funcionamiento | 14/05/2026 |
---

## Entregables principales: 
- Aplicación
- Código fuente
- Documentación
- Manual de despliegue
---

## Riesgos: 

| ID | Riesgo | Probabilidad | Impacto | Mitigación |
|----|--------|-------------|---------|------------|
| R1 | Pérdida o baja disponibilidad de un miembro del equipo | Media | Alta | Redistribución de tareas mediante reunión extraordinaria convocada por el rol de integración esa semana |
| R2 | Fallo técnico o incompatibilidad de tecnologías o dependencias | Media | Alta | Evaluación temprana de alternativas y posible migración |
| R3 | Suspensión o cambio de prioridades por parte del cliente | Baja | Alta | Documentar estado del proyecto y reorganizar backlog |
---

## Comunicación: 
- Comunicación presencial en el laboratorio. 
- En caso de no poder asistir, comunicación mediante issues. 
- Reunión cada jueves al finalizar un hito. El responsable de integración de cada semana se encargará de convocar la reunión y preparar el orden del día.  
---

## Partes interesadas:
- Cliente / profesor de taller transversal I
- Equipo de desarrollo
- Compañeros del grupo informático
---

## Criterio de éxito: 
El proyecto se considera completado con éxito si: 
- la aplicación cumple las reglas definidas,
- el servicio funciona en un entorno real o de pruebas,
- el código está organizado y documentado,
- y la entrega final puede defenderse con claridad.
---







## Tipos de entidades:
- Se queda quieta.
- Se mueve cada instante a una casilla adyacente de manera aleatoria para arriba, abajo, derecha e izquierda.
- Se mantiene en su casilla y cada instante tiene una probabilidad que elegimos nosotros para moverse a una casilla de la cruceta.
- Si ya hay un color en la casilla, prevalece el color que ya existía.

## Decisiones de tecnología:
- Framework: Spring Boot, porque nos permite crear la API REST de forma rápida y ordenada. Nos ahorra mucha configuración y facilita exponer endpoints para que la web consuma el servicio.
- Lenguaje: Java 17 (el equipo está familiarizado con java y ahorra tiempo de formación)
- Tipo API: REST JSON
- Build tool: Maven (por la gestión de dependencias que conoce el equipo)


