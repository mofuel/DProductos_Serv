package com.proyecto.Controller;

import com.proyecto.Model.RouteRequest;
import com.proyecto.Service.RouteRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/route-requests")
public class RouteRequestController {

    @Autowired
    private RouteRequestService routeRequestService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("requests", routeRequestService.findAll());
        return "entregamedicamento"; // Vista HTML
    }

    @PostMapping("/create")
    public String crear(RouteRequest request) {
        routeRequestService.save(request);
        return "entregamedicamento";
    }
}
