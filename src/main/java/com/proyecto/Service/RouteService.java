package com.proyecto.Service;

import com.proyecto.Model.RouteRequest;
import com.proyecto.Model.RouteResult;
import com.proyecto.Repository.RouteRequestRepository;
import com.proyecto.Repository.RouteResultRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRequestRepository requestRepo;

    @Autowired
    private RouteResultRepository resultRepo;

    @Transactional
    public RouteResult guardarRutaYObtenerResultado(RouteRequest request, RouteResult result) {
        // Guardar la solicitud (RouteRequest)
        RouteRequest savedRequest = requestRepo.save(request);

        // Asignar el request guardado al resultado
        result.setRequest(savedRequest);

        // Guardar el resultado (RouteResult)
        RouteResult savedResult = resultRepo.save(result);

        // No guardamos los pasos, solo retornamos resultado para enviar junto con los pasos calculados en memoria
        return savedResult;
    }

    @Transactional
    public void guardarRutaCompleta(RouteRequest request, RouteResult result) {
        RouteRequest savedRequest = requestRepo.save(request);
        result.setRequest(savedRequest);
        resultRepo.save(result);
    }

}
