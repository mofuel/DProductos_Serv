package com.proyecto.Repository;

import com.proyecto.Model.HorarioMedicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface HorarioMedicacionRepository extends JpaRepository<HorarioMedicacion, Long> {
    List<HorarioMedicacion> findByUsuarioId(Long usuarioId);
    List<HorarioMedicacion> findByHora(LocalTime hora);
}
