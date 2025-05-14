package com.proyecto.Service;

import com.proyecto.Model.Medicamento;
import com.proyecto.Repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    public List<Medicamento> listarPorUsuario(Long usuarioId) {
        return medicamentoRepository.findByUsuarioId(usuarioId);
    }

    public Medicamento guardar(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    public Medicamento obtenerPorId(Long id) {
        return medicamentoRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        medicamentoRepository.deleteById(id);
    }

    public Medicamento findById(Long id) {
        return medicamentoRepository.findById(id).orElse(null);
    }

}
