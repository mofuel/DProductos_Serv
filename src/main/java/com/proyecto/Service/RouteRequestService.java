package com.proyecto.Service;

import com.proyecto.Model.RouteRequest;
import com.proyecto.Repository.RouteRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteRequestService {
    @Autowired
    private RouteRequestRepository routeRequestRepository;


    public RouteRequest save(RouteRequest request) {
        return routeRequestRepository.save(request);
    }

    public Optional<RouteRequest> findById(Long id) {
        return routeRequestRepository.findById(id);
    }

    public List<RouteRequest> findAll() {
        return routeRequestRepository.findAll();
    }

    public void deleteById(Long id) {
        routeRequestRepository.deleteById(id);
    }
}
