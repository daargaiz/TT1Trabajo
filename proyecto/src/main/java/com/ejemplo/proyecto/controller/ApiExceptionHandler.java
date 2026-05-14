package com.ejemplo.proyecto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador centralizado de errores de validación de la API.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Transforma errores de argumentos inválidos en respuestas HTTP 400.
     * @param ex Excepción lanzada por la lógica o los controladores.
     * @return Detalle del problema para el cliente.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        detail.setTitle("Bad Request");
        return detail;
    }
}
