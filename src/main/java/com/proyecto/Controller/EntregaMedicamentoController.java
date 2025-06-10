package com.proyecto.Controller;

import com.proyecto.Model.*;
import com.proyecto.Repository.NotificacionRepository;
import com.proyecto.Service.EntregaMedicamentoService;
import com.proyecto.Service.HorarioMedicacionService;
import com.proyecto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/entregamedicamento")
public class EntregaMedicamentoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private HorarioMedicacionService horarioMedicacionService;

    @Autowired
    private EntregaMedicamentoService entregaMedicamentoService;

    @Autowired
    private NotificacionRepository notificacionRepository;

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
            Usuario usuario = horario.getusuario();

            // 🕒 Validación del horario lógico (10 minutos de tolerancia)
            LocalTime horaActual = LocalTime.now();
            LocalTime horaProgramada = horario.getHora();
            long minutosDiferencia = Math.abs(ChronoUnit.MINUTES.between(horaActual, horaProgramada));

            if (minutosDiferencia > 10) {
                return "Horario inválido. Solo puedes tomar el medicamento dentro de 10 minutos antes o después de la hora programada.";
            }

            // ✅ Registrar entrega si el horario es válido
            EntregaMedicamento entrega = new EntregaMedicamento();
            entrega.setHorarioMedicacion(horario);
            entrega.setFechaEntrega(LocalDateTime.now());
            entrega.setEntregado(true);
            entrega.setConfirmado(confirmado);
            entrega.setObservaciones(confirmado ? "Confirmado por el usuario" : "Usuario indicó que no tomó el medicamento");

            entregaMedicamentoService.guardar(entrega);

            // Incidencia si no lo tomó
            if (!confirmado) {
                Notificacion notificacion = new Notificacion();
                notificacion.setUsuario(usuario);
                notificacion.setMensaje("El usuario no tomó el medicamento: " + horario.getMedicamento().getNombre());
                notificacion.setFechaProgramada(LocalDateTime.now());
                notificacion.setEstado(Notificacion.EstadoNotificacion.enviada);
                notificacion.setTipo("incidencia");
                notificacion.setCreatedAt(LocalDateTime.now());

                notificacionRepository.save(notificacion);
            }

            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al registrar la entrega: " + e.getMessage();
        }
    }


    @PostMapping("/verificar")
    @ResponseBody
    public Map<String, String> verificarHorario(@RequestBody Map<String, Long> datos) {
        Map<String, String> response = new HashMap<>();
        try {
            Long horarioId = datos.get("horarioId");
            HorarioMedicacion horario = horarioMedicacionService.obtenerPorId(horarioId);

            if (horario == null) {
                response.put("estado", "ERROR");
                response.put("mensaje", "Horario no encontrado.");
                return response;
            }

            // Combina fecha y hora programada
            LocalDateTime fechaHoraProgramada = LocalDateTime.of(horario.getFecha(), horario.getHora());
            LocalDateTime ahora = LocalDateTime.now();

            // Diferencia en minutos
            long minutosDiferencia = ChronoUnit.MINUTES.between(fechaHoraProgramada, ahora);
            long minutosAbs = Math.abs(minutosDiferencia);

            // Formateo de fecha y hora para el frontend
            DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("hh:mm a", new Locale("es", "ES"));
            DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
            response.put("horaProgramada", horario.getHora().format(horaFormatter));
            response.put("fechaProgramada", horario.getFecha().format(fechaFormatter));

            // Cálculo de diferencia amigable
            String diferenciaStr;
            if (minutosAbs < 60) {
                diferenciaStr = minutosAbs + " minuto(s)";
            } else if (minutosAbs < 1440) {
                long horas = minutosAbs / 60;
                diferenciaStr = horas + " hora(s)";
            } else {
                long dias = minutosAbs / 1440;
                diferenciaStr = dias + " día(s)";
            }

            response.put("diferencia", diferenciaStr);

            // Validación del rango permitido de 10 minutos
            if (minutosAbs > 10) {
                response.put("estado", "FUERA_DE_RANGO");
                response.put("direccion", minutosDiferencia > 0 ? "tarde" : "temprano");
            } else {
                response.put("estado", "OK");
            }

            return response;
        } catch (Exception e) {
            response.put("estado", "ERROR");
            response.put("mensaje", "Error interno: " + e.getMessage());
            return response;
        }
    }





}
