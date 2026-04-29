package com.ejemplo.proyecto.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Email")
public class EmailController {

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String enviar(
            @RequestParam String emailAddress,
            @RequestParam String message
    ) {
        if (emailAddress == null || emailAddress.isBlank() || message == null || message.isBlank()) {
            return "";
        }
        return "accepted";
    }
}
