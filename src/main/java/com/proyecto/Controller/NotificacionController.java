package com.proyecto.Controller;

import com.proyecto.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificacionController {

    @Autowired
    private EmailService emailService;


    @RequestMapping("/enviarCorreo")
    public String enviarCorreo() {
        String to = "miguel.monzon.lucio@gmail.com";
        String subject = "Recordatorio de medicación";
        String body = "Es hora de tomar tu medicamento. No olvides seguir las indicaciones de tu médico.";

        emailService.sendEmail(to, subject, body);

        return "Correo enviado!";
    }
}
