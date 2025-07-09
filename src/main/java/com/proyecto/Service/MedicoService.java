package com.proyecto.Service;

import com.proyecto.Model.Medico;
import com.proyecto.Repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public Optional<Medico> obtenerMedicoDisponible() {
        LocalTime horaActual = LocalTime.now();
        return medicoRepository.encontrarMedicoDisponible(horaActual);
    }
}
