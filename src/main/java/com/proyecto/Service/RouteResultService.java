package com.proyecto.Service;

import com.proyecto.Model.RouteResult;
import com.proyecto.Repository.RouteResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteResultService {

    @Autowired
    private RouteResultRepository routeResultRepository;

    public RouteResult save(RouteResult result) {
        return routeResultRepository.save(result);
    }

    public Optional<RouteResult> findById(Long id) {
        return routeResultRepository.findById(id);
    }

    public List<RouteResult> findAll() {
        return routeResultRepository.findAll();
    }

    public void deleteById(Long id) {
        routeResultRepository.deleteById(id);
    }
    public RouteResult findByRequestId(Long requestId) {
        return routeResultRepository.findByRequestId(requestId);
    }
}
