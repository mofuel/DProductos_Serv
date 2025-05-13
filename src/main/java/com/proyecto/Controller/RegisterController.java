package com.proyecto.Controller;

import com.proyecto.Service.UsuarioService;
import com.proyecto.Model.registerDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("registerDTO") @Valid registerDTO registerDTO,
                                   BindingResult result, Model model) {


        // Verifica si hay errores de validación
        if (result.hasErrors()) {
            System.out.println("Errores de validación encontrados:");
            result.getAllErrors().forEach(error -> System.out.println(error.toString()));
            return "registro";
        }

        try {
            usuarioService.registrarUsuario(registerDTO);
            System.out.println("Usuario registrado correctamente");
            return "redirect:/login";
        } catch (Exception e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
            model.addAttribute("error", "Error al registrar el usuario");
            return "registro";
        }
    }

}
