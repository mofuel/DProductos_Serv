package com.proyecto.Repository;

import com.proyecto.Model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    @Query("SELECT m FROM Medico m WHERE :horaActual BETWEEN m.horaInicio AND m.horaFin AND m.disponible = true")
    Optional<Medico> encontrarMedicoDisponible(LocalTime horaActual);
}
