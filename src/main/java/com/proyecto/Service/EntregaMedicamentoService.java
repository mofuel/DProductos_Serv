package com.proyecto.Service;

import com.proyecto.Model.EntregaMedicamento;
import com.proyecto.Repository.EntregaMedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregaMedicamentoService {

    @Autowired
    private EntregaMedicamentoRepository entregaMedicamentoRepository;

    public void guardar(EntregaMedicamento entrega) {
        entregaMedicamentoRepository.save(entrega);
    }

    public List<EntregaMedicamento> listarPorUsuario(Long usuarioId) {
        return entregaMedicamentoRepository.findByHorarioMedicacionUsuarioId(usuarioId);
    }

    public EntregaMedicamento obtenerPorId(Long id) {
        return entregaMedicamentoRepository.findById(id).orElse(null);
    }
}
