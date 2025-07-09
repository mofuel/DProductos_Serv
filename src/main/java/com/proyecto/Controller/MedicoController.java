package com.proyecto.Controller;

import com.proyecto.Model.Medico;
import com.proyecto.Service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/disponible")
    public ResponseEntity<?> obtenerMedicoDisponible() {
        Optional<Medico> medicoOpt = medicoService.obtenerMedicoDisponible();

        if (medicoOpt.isPresent()) {
            return ResponseEntity.ok(medicoOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay médicos disponibles en este momento.");
        }
    }
}
