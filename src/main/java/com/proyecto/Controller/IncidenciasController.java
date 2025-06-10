package com.proyecto.Controller;

import com.proyecto.Model.Notificacion;
import com.proyecto.Model.Usuario;
import com.proyecto.Repository.NotificacionRepository;
import com.proyecto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IncidenciasController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @GetMapping("/notificaciones/incidencias")
    public String verIncidencias(@AuthenticationPrincipal User userDetails, Model model) {

        if (userDetails == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.findByCorreo(userDetails.getUsername());
        if (usuario == null) {
            return "redirect:/login";
        }

        List<Notificacion> incidencias = notificacionRepository.findAll();
        model.addAttribute("incidencias", incidencias);

        return "incidencias";
    }


}
