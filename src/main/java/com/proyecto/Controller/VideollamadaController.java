package com.proyecto.Controller;

import com.proyecto.Model.Usuario;
import com.proyecto.Model.Videollamada;
import com.proyecto.Service.UsuarioService;
import com.proyecto.Service.VideollamadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/videollamada")
public class VideollamadaController {

    @Autowired
    private VideollamadaService videollamadaService;

    @Autowired
    private UsuarioService usuarioService;

    // Mostrar vista principal de videollamadas
    @GetMapping
    public String mostrarVistaVideollamada() {
        return "videollamada"; // asegúrate de que exista videollamada.html en templates
    }

    // Iniciar videollamada simulada y guardarla en la base de datos
    @PostMapping("/iniciar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> iniciarVideollamada(@AuthenticationPrincipal User userDetails) {
        try {
            Usuario usuario = usuarioService.findByCorreo(userDetails.getUsername());

            Videollamada videollamada = new Videollamada();
            videollamada.setUsuario(usuario);
            videollamadaService.registrarVideollamada(videollamada);

            Map<String, Object> response = new HashMap<>();
            response.put("id", videollamada.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error al iniciar videollamada"));
        }
    }



    // Mostrar historial de videollamadas
    @GetMapping("/historial")
    public String verHistorial(@AuthenticationPrincipal User userDetails, Model model) {
        Usuario usuario = usuarioService.findByCorreo(userDetails.getUsername());
        model.addAttribute("videollamadas", videollamadaService.obtenerPorUsuario(usuario.getId()));
        return "historial_videollamadas"; // otra vista opcional si deseas mostrarlo
    }


    @PutMapping("/finalizar/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> finalizarVideollamada(@PathVariable Long id) {
        try {
            Videollamada videollamada = videollamadaService.finalizarVideollamada(id);
            Map<String, Object> response = new HashMap<>();
            response.put("duracion", videollamada.getDuracion());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error al finalizar la videollamada"));
        }
    }
}
