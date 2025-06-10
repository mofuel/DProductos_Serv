package com.proyecto.Controller;

import com.proyecto.Model.RouteResult;
import com.proyecto.Service.RouteResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/route-results")
public class RouteResultController {

    @Autowired
    private RouteResultService routeResultService;

    @GetMapping("/{requestId}")
    public String verResultado(@PathVariable Long requestId, Model model) {
        RouteResult result = routeResultService.findByRequestId(requestId);
        model.addAttribute("result", result);
        return "route-results/detail"; // Vista HTML
    }
}
