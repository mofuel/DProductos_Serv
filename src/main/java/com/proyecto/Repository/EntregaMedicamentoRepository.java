package com.proyecto.Repository;

import com.proyecto.Model.EntregaMedicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntregaMedicamentoRepository extends JpaRepository<EntregaMedicamento, Long> {
    List<EntregaMedicamento> findByHorarioMedicacionUsuarioId(Long usuarioId);
}
