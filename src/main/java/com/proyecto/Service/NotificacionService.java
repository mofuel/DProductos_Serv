package com.proyecto.Service;

import com.proyecto.Model.HorarioMedicacion;
import com.proyecto.Model.Usuario;
import com.proyecto.Repository.HorarioMedicacionRepository;
import com.proyecto.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private HorarioMedicacionRepository horarioMedicacionRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 60000) // cada 60 segundos
    public void verificarHorariosYNotificar() {
        LocalTime ahora = LocalTime.now().withSecond(0).withNano(0); // hora exacta sin segundos

        List<HorarioMedicacion> horarios = horarioMedicacionRepository.findByHora(ahora);
        for (HorarioMedicacion horario : horarios) {
            Usuario usuario = horario.getusuario(); // suponiendo que tienes una relación con Usuario

            if (usuario != null && usuario.getCorreo() != null) {
                String mensaje = "Hola " + usuario.getNombre() + ",\nEs hora de tomar tu medicamento: " + horario.getMedicamento().getNombre();
                emailService.sendEmail(usuario.getCorreo(), "Recordatorio de Medicamento", mensaje);
            }
        }
    }
}
