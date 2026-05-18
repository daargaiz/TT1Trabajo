package com.ejemplo.proyecto.businesslogic.printer;

/**
 * Contrato para imprimir información de una entidad.
 */
public interface IEntidadPrinter {
    /**
     * Genera la descripción textual de la entidad.
     * @return Texto con la información de la entidad.
     */
    public String imprimirInformacion();
}
