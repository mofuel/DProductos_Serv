package com.proyecto.Controller;

import com.proyecto.Model.registerDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {

    @GetMapping("/index")
    public String mostrarIndex(){
        return "index";
    }

    @GetMapping("/contactanos")
    public String mostrarContactanos(){
        return "contactanos";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model){
        model.addAttribute("registerDTO", new registerDTO());
        return "registro";
    }
}
