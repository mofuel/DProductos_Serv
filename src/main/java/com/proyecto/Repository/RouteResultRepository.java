package com.proyecto.Repository;

import com.proyecto.Model.RouteResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteResultRepository extends JpaRepository<RouteResult, Long> {
    RouteResult findByRequestId(Long requestId);

}
