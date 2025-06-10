package com.proyecto.Controller;


import com.proyecto.Model.RouteRequest;
import com.proyecto.Model.RouteResult;
import com.proyecto.Model.RutaDto;
import com.proyecto.Model.Usuario;
import com.proyecto.Service.RouteService;
import com.proyecto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/robot")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/mover")
    public ResponseEntity<?> moverRobot(@RequestBody RutaDto rutaDto,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        // Obtener el usuario autenticado por su correo (username)
        Usuario usuario = usuarioService.findByCorreo(userDetails.getUsername());

        // Construir RouteRequest
        RouteRequest request = new RouteRequest();
        request.setStartRow(rutaDto.getStartRow());
        request.setStartCol(rutaDto.getStartCol());
        request.setEndRow(rutaDto.getEndRow());
        request.setEndCol(rutaDto.getEndCol());
        request.setUsuario(usuario);

        // Construir RouteResult
        RouteResult result = new RouteResult();
        result.setTotalDistance(rutaDto.getTotalDistance());

        // Guardar request y result (sin steps)
        routeService.guardarRutaCompleta(request, result);

        // Respuesta como JSON
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Ruta guardada correctamente");
        return ResponseEntity.ok(response);
    }

}


