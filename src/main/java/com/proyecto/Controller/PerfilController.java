package com.proyecto.Controller;

import com.proyecto.Model.Usuario;
import com.proyecto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/perfil")
    public String verPerfil(Model model) {
        // Obtener el correo del usuario autenticado
        String correo = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Buscar los datos del usuario
        Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(correo);

        // Pasar los datos al modelo
        model.addAttribute("usuario", usuario);

        return "perfil";
    }


    @PostMapping("/perfil")
    public String actualizarPerfil(@ModelAttribute("usuario") Usuario usuarioActualizado, Principal principal) {
        // Este metodo debe llamar al servicio
        usuarioService.actualizarPerfil(usuarioActualizado, principal.getName());
        return "redirect:/perfil?exito";
    }
}
