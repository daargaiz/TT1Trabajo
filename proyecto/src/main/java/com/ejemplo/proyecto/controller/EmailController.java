package com.ejemplo.proyecto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Email")
public class EmailController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailResponse> enviar(
            @RequestParam String emailAddress,
            @RequestParam String message
    ) {
        if (emailAddress == null || emailAddress.isBlank() || message == null || message.isBlank()) {
            throw new IllegalArgumentException("Los parametros de email son obligatorios");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new EmailResponse(true, null));
    }

    public record EmailResponse(boolean done, String errorMessage) {
    }
}
