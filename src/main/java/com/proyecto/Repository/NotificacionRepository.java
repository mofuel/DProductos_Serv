package com.proyecto.Repository;

import com.proyecto.Model.Notificacion;
import com.proyecto.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository  extends JpaRepository<Notificacion, Long> {


}
