package com.proyecto.Controller;

import com.proyecto.Model.EntregaMedicamento;
import com.proyecto.Model.HorarioMedicacion;
import com.proyecto.Model.Usuario;
import com.proyecto.Service.EntregaMedicamentoService;
import com.proyecto.Service.HorarioMedicacionService;
import com.proyecto.Service.UsuarioService;
import com.proyecto.Model.EntregaMedicamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/entregamedicamento")
public class EntregaMedicamentoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private HorarioMedicacionService horarioMedicacionService;

    @Autowired
    private EntregaMedicamentoService entregaMedicamentoService;

    // Mostrar la vista de entregas de medicamentos
    @GetMapping
    public String mostrarEntregas(Model model, @AuthenticationPrincipal User userDetails) {
        Usuario usuario = usuarioService.findByCorreo(userDetails.getUsername());
        List<HorarioMedicacion> horarios = horarioMedicacionService.listarPorUsuario(usuario.getId());

        model.addAttribute("horarios", horarios);
        model.addAttribute("entrega", new EntregaMedicamento());
        return "entregamedicamento";  // Asegúrate de que esta sea la ruta correcta del archivo .html
    }

    // Registrar la entrega de medicamento
    @PostMapping("/registrar")
    @ResponseBody
    public String registrarEntrega(@RequestBody EntregaMedicamentoDTO entregaMedicamentoDTO) {
        try {
            Long horarioId = entregaMedicamentoDTO.getHorarioId();
            boolean confirmado = entregaMedicamentoDTO.getConfirmado();

            HorarioMedicacion horario = horarioMedicacionService.obtenerPorId(horarioId);

            EntregaMedicamento entrega = new EntregaMedicamento();
            entrega.setHorarioMedicacion(horario);
            entrega.setFechaEntrega(LocalDateTime.now());
            entrega.setEntregado(true);
            entrega.setConfirmado(confirmado);
            entrega.setObservaciones(confirmado ? "Confirmado por el usuario" : "Usuario indicó que no tomó el medicamento");

            entregaMedicamentoService.guardar(entrega);

            return "OK";
        } catch (Exception e) {
            e.printStackTrace();  // Esto te ayudará a ver el error específico en la consola
            return "Error al registrar la entrega: " + e.getMessage();  // Devuelve el error para ayudar a depurar
        }
    }



}
