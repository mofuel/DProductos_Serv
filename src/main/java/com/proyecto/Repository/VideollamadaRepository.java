package com.proyecto.Repository;

import com.proyecto.Model.Videollamada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideollamadaRepository extends JpaRepository<Videollamada, Long> {
    List<Videollamada> findByUsuarioId(Long usuarioId);
}
