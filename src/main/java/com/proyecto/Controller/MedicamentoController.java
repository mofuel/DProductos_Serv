package com.proyecto.Controller;

import com.proyecto.Model.Medicamento;
import com.proyecto.Model.Usuario;
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
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private UsuarioService usuarioService;

    // Listar medicamentos (para la vista medicamentos.html)
    @GetMapping("/medicamentos")
    public String listarMedicamentos(Model model, @AuthenticationPrincipal User userDetails) {
        // Verifica el usuario
        Usuario usuario = usuarioService.findByCorreo(userDetails.getUsername());

        // Recupera la lista de medicamentos del usuario
        List<Medicamento> medicamentos = medicamentoService.listarPorUsuario(usuario.getId());

        // Agrega los medicamentos al modelo
        model.addAttribute("medicamentos", medicamentos);

        return "medicamentos"; // Vista que muestra la lista de medicamentos
    }

    // Gestión de medicamentos (para la vista agregarmedicamentos.html)
    @GetMapping("/agregarmedicamentos")
    public String gestionMedicamentos(Model model, @AuthenticationPrincipal User userDetails) {
        // Verifica el usuario
        Usuario usuario = usuarioService.findByCorreo(userDetails.getUsername());

        // Recupera la lista de medicamentos del usuario
        List<Medicamento> medicamentos = medicamentoService.listarPorUsuario(usuario.getId());

        // Agrega los medicamentos y un nuevo medicamento vacío al modelo
        model.addAttribute("medicamentos", medicamentos);
        model.addAttribute("medicamento", new Medicamento()); // Pasar el objeto 'medicamento' vacío para el formulario

        return "agregarmedicamentos"; // Vista para agregar o gestionar medicamentos
    }


    // Guardar un nuevo medicamento o editar uno existente
    @PostMapping("/agregarmedicamentos")
    public String guardar(@ModelAttribute Medicamento medicamento, @AuthenticationPrincipal User userDetails) {
        // Obtener el usuario autenticado
        Usuario usuario = usuarioService.findByCorreo(userDetails.getUsername());
        medicamento.setUsuario(usuario);  // Asignar el usuario al medicamento

        // Si el medicamento tiene un ID, es una edición; si no, es una creación
        medicamentoService.guardar(medicamento);

        return "redirect:/agregarmedicamentos";  // Redirigir a la página de agregar/gestionar medicamentos
    }


    // Eliminar un medicamento
    @GetMapping("/agregarmedicamentos/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        medicamentoService.eliminar(id);
        return "redirect:/agregarmedicamentos";  // Redirigir a la página de agregar/gestionar medicamentos
    }


    // Cargar los datos de un medicamento para editarlo
    @GetMapping("/agregarmedicamentos/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        // Obtener el medicamento por su ID
        Medicamento medicamento = medicamentoService.obtenerPorId(id);

        // Agregar el medicamento al modelo
        model.addAttribute("medicamento", medicamento);

        // Retorna la vista que contiene el formulario de edición
        return "agregarmedicamentos";  // Retorna a la página de agregar/gestionar medicamentos
    }

    @PostMapping("/medicamentos/editar/{id}")
    public String editar(@PathVariable Long id, @ModelAttribute Medicamento medicamento, @AuthenticationPrincipal User userDetails) {
        // Obtener el medicamento por su ID
        Medicamento medicamentoExistente = medicamentoService.obtenerPorId(id);

        // Actualizar el medicamento con los nuevos datos del formulario
        medicamentoExistente.setNombre(medicamento.getNombre());
        medicamentoExistente.setDosis(medicamento.getDosis());

        // Guardar el medicamento actualizado
        medicamentoService.guardar(medicamentoExistente);

        // Redirigir a la lista de medicamentos o donde prefieras
        return "redirect:/agregarmedicamentos";
    }


}
