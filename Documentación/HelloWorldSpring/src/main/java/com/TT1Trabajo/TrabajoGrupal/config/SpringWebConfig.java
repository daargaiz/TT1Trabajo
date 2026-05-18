package com.TT1Trabajo.TrabajoGrupal.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configuración web básica para activar Spring MVC y el escaneo de componentes.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.TT1Trabajo")
public class SpringWebConfig {
}
