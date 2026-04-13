package com.TT1Trabajo.TrabajoGrupal.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.TT1Trabajo")
public class SpringWebConfig {
}
