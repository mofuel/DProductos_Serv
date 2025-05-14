package com.proyecto.Controller;


import com.proyecto.Model.EntregaMedicamento;
import com.proyecto.Model.Medicamento;
import com.proyecto.Repository.EntregaMedicamentoRepository;
import com.proyecto.Repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HistorialMedicamentosController {

    @Autowired
    private EntregaMedicamentoRepository entregaMedicamentoRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @GetMapping("/historialmedicamentos")
    public String verHistorial(Model model) {
        List<EntregaMedicamento> entregas = entregaMedicamentoRepository.findAll();

        List<Map<String, Object>> entregasData = new ArrayList<>();

        for (EntregaMedicamento entrega : entregas) {
            Map<String, Object> entregaData = new HashMap<>();

            if (entrega.getHorarioMedicacion() != null && entrega.getHorarioMedicacion().getMedicamento() != null) {
                Medicamento medicamento = entrega.getHorarioMedicacion().getMedicamento();
                entregaData.put("medicamentoNombre", medicamento.getNombre());
                entregaData.put("hora", entrega.getHorarioMedicacion().getHora());
                entregaData.put("confirmado", entrega.getConfirmado());
                entregaData.put("fechaEntrega", entrega.getFechaEntrega()); // 🟢 ahora se incluye
            }

            entregasData.add(entregaData);
        }

        model.addAttribute("entregas", entregasData);
        return "historialmedicamentos";
    }


}
