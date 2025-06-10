package com.proyecto.Controller;

import com.proyecto.Model.Medicamento;
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

    @GetMapping("/dashboard")
    public String mostrarDashboard(){
        return "dashboard";
    }

    @GetMapping("/informacion")
    public String mostrarInformacion(){
        return "informacion";
    }


    @GetMapping("/nosotros")
    public String mostrarContactanos(){
        return "nosotros";
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
