package com.proyecto.Repository;

import com.proyecto.Model.RouteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RouteRequestRepository extends JpaRepository<RouteRequest, Long> {
    List<RouteRequest> findByUsuarioId(Long usuarioId);
}
