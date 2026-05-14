package com.ejemplo.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación Spring Boot.
 */
@SpringBootApplication
public class ProyectoApplication {

	/**
	 * Arranca el contexto de Spring y la aplicación web.
	 * @param args Argumentos de arranque recibidos por la JVM.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

}
