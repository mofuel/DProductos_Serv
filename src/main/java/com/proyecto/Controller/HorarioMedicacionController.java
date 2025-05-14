package com.proyecto.Controller;


import com.proyecto.Model.HorarioMedicacion;
import com.proyecto.Model.Medicamento;
import com.proyecto.Model.Usuario;
import com.proyecto.Service.HorarioMedicacionService;
import com.proyecto.Service.MedicamentoService;
import com.proyecto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/horarios")
public class HorarioMedicacionController {

    @Autowired
    private HorarioMedicacionService horarioMedicacionService;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    public String mostrarHorarios(Model model, @AuthenticationPrincipal User userDetails) {
        // Llamada al servicio para obtener los horarios y medicamentos
        List<HorarioMedicacion> horarios = horarioMedicacionService.listarHorariosPorUsuario(userDetails.getUsername());
        List<Medicamento> medicamentos = horarioMedicacionService.listarMedicamentosPorUsuario(userDetails.getUsername());

        // Enviar los datos al modelo
        model.addAttribute("horarios", horarios);
        model.addAttribute("medicamentos", medicamentos);
        model.addAttribute("horario", new HorarioMedicacion());

        return "horariomedicacion";
    }

    @PostMapping("/guardar")
    public String guardarHorario(@ModelAttribute("horario") HorarioMedicacion horarioMedicacion,
                                 @AuthenticationPrincipal User userDetails) {

        Usuario usuario = usuarioService.findByCorreo(userDetails.getUsername());
        horarioMedicacion.setusuario(usuario);

        // Llamada al servicio para gestionar el medicamento
        horarioMedicacionService.asignarMedicamento(horarioMedicacion);

        // Guardar en la base de datos
        horarioMedicacionService.guardar(horarioMedicacion);
        return "redirect:/horarios";
    }

    @PostMapping("/editar/{id}")
    public String editarHorario(@PathVariable Long id,
                                @ModelAttribute HorarioMedicacion horarioEditado,
                                @AuthenticationPrincipal User userDetails) {
        // Buscar el horario original en la base de datos
        HorarioMedicacion horarioExistente = horarioMedicacionService.obtenerPorId(id);

        // Obtener el usuario autenticado y asociarlo (por seguridad)
        Usuario usuario = usuarioService.findByCorreo(userDetails.getUsername());
        horarioExistente.setusuario(usuario);

        // Actualizar campos desde el formulario
        horarioExistente.setFecha(horarioEditado.getFecha());
        horarioExistente.setHora(horarioEditado.getHora());
        horarioExistente.setFrecuencia(horarioEditado.getFrecuencia());

        // Obtener el medicamento por ID si fue seleccionado en el formulario
        if (horarioEditado.getMedicamento() != null && horarioEditado.getMedicamento().getId() != null) {
            Medicamento medicamento = medicamentoService.findById(horarioEditado.getMedicamento().getId());
            if (medicamento != null) {
                horarioExistente.setMedicamento(medicamento);
            }
        }

        // Guardar el horario actualizado
        horarioMedicacionService.guardar(horarioExistente);

        return "redirect:/horarios";
    }








    @GetMapping("/eliminar/{id}")
    public String eliminarHorario(@PathVariable Long id) {
        horarioMedicacionService.eliminar(id);
        return "redirect:/horarios";
    }
}
