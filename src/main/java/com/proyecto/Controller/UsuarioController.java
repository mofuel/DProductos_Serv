package com.proyecto.Controller;

import com.proyecto.Model.Usuario;
import com.proyecto.Repository.UsuarioRepository;
import com.proyecto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios/{id}")
    public String obtenerUsuario(@PathVariable int id, Model model) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        usuarioOpt.ifPresent(usuario -> model.addAttribute("usuario", usuario));
        return "usuarios";
    }

    @PostMapping
    @ResponseBody
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }
}
