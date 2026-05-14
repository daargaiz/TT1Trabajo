package com.TT1Trabajo.TrabajoGrupal.config;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Inicializador web para registrar la configuración MVC sin archivo web.xml.
 */
public class WebAppInitializer extends  AbstractAnnotationConfigDispatcherServletInitializer{
    /**
     * No se usa configuración raíz adicional.
     * @return null porque toda la configuración está en el servlet.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    /**
     * Indica la configuración específica del servlet de Spring MVC.
     * @return Clase de configuración web.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{SpringWebConfig.class};
    }

    /**
     * Mapea el servlet principal a toda la aplicación.
     * @return Patrón de rutas atendidas por Spring MVC.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
