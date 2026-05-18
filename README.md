# 🧫 Isla de Petri
## Servicio de entidades en un tablero

![Versión](https://img.shields.io/badge/versión-v5.7.6-blue)
![Build](https://img.shields.io/badge/build-8884-green)
![Número de la suerte](https://img.shields.io/badge/suerte-69-orange)

> [!IMPORTANT]
> El código de este repositorio se proporciona tal cual, sin garantías de ningún tipo.
> Los autores no se responsabilizan de ningún daño directo o indirecto que pueda derivarse
> de su uso, incluyendo pérdida de datos, fallos del sistema o cualquier otro perjuicio.
> **Úsalo bajo tu propia responsabilidad.** Verifica siempre los outputs antes de
> utilizarlos en entornos de producción o para tomar decisiones.

## Descripción 

Este proyecto implementa un servicio backend en Java para la gestión y movimiento de entidades sobre un tablero. 
Expone una API que permite controlar la posición y el comportamiento de elementos (figuras) dentro de un espacio bidimensional.

## Requisitos Previos

- **Java 17** o superior → [Descargar Java 17](https://adoptium.net/es/temurin/releases/?version=17)
- **Maven** → [Descargar Maven](https://maven.apache.org/download.cgi)
- Spring Boot → [Guía de instalación](https://spring.io/projects/spring-boot)

## Cómo Ejecutar

1. Clona el repositorio:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd proyecto
   ```
2. Navega al punto de entrada de la aplicación:
    ```
   src/main/java/com/ejemplo/proyecto/ProyectoApplication.java
   ```
3. Ejecuta el proyecto desde tu IDE o mediante línea de comandos
    ```
   ./mvnw spring-boot:run
   # o en Windows:
   mvnw.cmd spring-boot:run
   ```
4. El servicio estará disponible en ``http://localhost:8080`` (puedes configurar otro puerto en application.properties). 

## Interacción con el Cliente

Este backend está diseñado para funcionar junto con el trabajo individual de daargaiz, que actúa como interfaz de usuario o cliente consumidor de la API.

Para visualizar el movimiento de las entidades del tablero:

1. Asegúrate de que este servicio de backend esté en ejecución.
2. Incia el cliente de daargaiz.
3. Desde el cliente, realiza las peticiones correspondientes para observar cómo se desplazan las figuras del tablero. 

## Errores comunes

| # | Error | Solución                                                                                                                                                                                                                                                             |
|---|-------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1 | No tienes alguna herramienta instalada | Comprueba que tienes la versión Java correspondiente con `java --version`.<br>Comprueba que tienes Maven instalado con `mvn --version`.<br>Comprueba que tienes Spring Boot.<br>Si falta alguno, sigue la instalación desde los enlaces de la sección de requisitos. |
| 2 | Error 404 | Verifica que está corriendo el trabajo individual.<br>Comprueba que no te has desconectado de la red.<br>Reinicia el ordenador si persiste el problema.<br>Si el error se mantiene, llama a servicio técnico.                                                        |
| 3 | Se te apaga el ordenador | Ponlo a cargar. Considera comprar un nuevo ordenador. |                                                                                                                                                                                                               |

## Autores:
-  Daniel Argaiz Garcia 
- Juan Pulgar Hernaiz 
- Víctor Esteban Chacobo
