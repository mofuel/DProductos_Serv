package com.proyecto.Service;


import com.proyecto.Model.HorarioMedicacion;
import com.proyecto.Model.Medicamento;
import com.proyecto.Model.Usuario;
import com.proyecto.Repository.HorarioMedicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioMedicacionService {

    @Autowired
    private HorarioMedicacionRepository horarioMedicacionRepository;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private UsuarioService usuarioService;

    public void guardar(HorarioMedicacion horario) {
        horarioMedicacionRepository.save(horario);
    }

    // Obtener horarios por usuario
    public List<HorarioMedicacion> listarHorariosPorUsuario(String correoUsuario) {
        Usuario usuario = usuarioService.findByCorreo(correoUsuario);
        return horarioMedicacionRepository.findByUsuarioId(usuario.getId());
    }


    public List<Medicamento> listarMedicamentosPorUsuario(String correoUsuario) {
        Usuario usuario = usuarioService.findByCorreo(correoUsuario);
        return medicamentoService.listarPorUsuario(usuario.getId());
    }

    public List<HorarioMedicacion> listarPorUsuario(Long usuarioId) {
        return horarioMedicacionRepository.findByUsuarioId(usuarioId);
    }

    public void eliminar(Long id) {
        horarioMedicacionRepository.deleteById(id);
    }

    public HorarioMedicacion obtenerPorId(Long id) {
        return horarioMedicacionRepository.findById(id).orElse(null);
    }

    public void asignarMedicamento(HorarioMedicacion horarioMedicacion) {
        if (horarioMedicacion.getMedicamento() != null) {
            Long medicamentoId = horarioMedicacion.getMedicamento().getId();
            Medicamento medicamento = medicamentoService.findById(medicamentoId);

            if (medicamento != null) {
                horarioMedicacion.setMedicamento(medicamento);
            }
        }
    }


}
