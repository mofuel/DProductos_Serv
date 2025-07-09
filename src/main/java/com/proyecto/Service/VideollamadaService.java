package com.proyecto.Service;

import com.proyecto.Model.Medico;
import com.proyecto.Model.Videollamada;
import com.proyecto.Repository.MedicoRepository;
import com.proyecto.Repository.VideollamadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VideollamadaService {

    @Autowired
    private VideollamadaRepository videollamadaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public void registrarVideollamada(Videollamada videollamada) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalTime horaActual = ahora.toLocalTime();

        // Buscar médico disponible según la hora actual
        List<Medico> medicosDisponibles = medicoRepository.findAll();
        Medico medicoAsignado = medicosDisponibles.stream()
                .filter(m -> horaActual.isAfter(m.getHoraInicio()) && horaActual.isBefore(m.getHoraFin()))
                .findFirst()
                .orElse(null);

        if (medicoAsignado == null) {
            throw new RuntimeException("No hay médicos disponibles en este momento.");
        }

        videollamada.setMedico(medicoAsignado);
        videollamada.setFechaInicio(ahora);
        videollamada.setFechaFin(ahora.plusMinutes(15)); // simulamos 15 minutos
        videollamada.setDuracion(15);
        videollamada.setEstado("FINALIZADA"); // como es simulada, ya la damos por terminada

        videollamadaRepository.save(videollamada);
    }

    public List<Videollamada> obtenerPorUsuario(Long usuarioId) {
        return videollamadaRepository.findByUsuarioId(usuarioId);
    }

    public Videollamada finalizarVideollamada(Long id) {
        Videollamada videollamada = videollamadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Videollamada no encontrada"));

        videollamada.setFechaFin(LocalDateTime.now());
        long duracion = ChronoUnit.MINUTES.between(videollamada.getFechaInicio(), videollamada.getFechaFin());
        videollamada.setDuracion((int) duracion);
        videollamada.setEstado("FINALIZADA");
        return videollamadaRepository.save(videollamada);
    }
}
