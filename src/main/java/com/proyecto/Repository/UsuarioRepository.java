package com.proyecto.Repository;

import com.proyecto.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Buscar usuario por correo (por ejemplo, para login o notificaciones)
    Optional<Usuario> findByCorreo(String correo);

    // Buscar por DNI
    Optional<Usuario> findByDni(String dni);

    // Validar si existe un correo
    boolean existsByCorreo(String correo);

}
