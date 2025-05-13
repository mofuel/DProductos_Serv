package com.proyecto.Service;

import com.proyecto.Model.registerDTO;
import com.proyecto.Model.Usuario;
import com.proyecto.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(int id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);  // No es necesario orElse(null), ya que findByCorreo devuelve un Optional
    }


    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public void registrarUsuario(registerDTO registerDTO) {
        // Verifica si las contraseñas coinciden
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmpassword())) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }

        // Encriptar la contraseña antes de guardar
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(registerDTO.getPassword());

        // Crear una nueva entidad de Usuario a partir del DTO
        Usuario usuario = new Usuario();
        usuario.setNombre(registerDTO.getNombre());
        usuario.setDni(registerDTO.getDni());
        usuario.setCorreo(registerDTO.getEmail());
        usuario.setPassword(passwordEncriptada); // Guardar la contraseña encriptada

        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);
    }

    // Obtener el usuario por su correo electrónico
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void actualizarPerfil(Usuario usuarioActualizado, String correoPrincipal) {
        Usuario usuarioExistente = usuarioRepository.findByCorreo(correoPrincipal)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setDni(usuarioActualizado.getDni());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());


        // Solo actualizar si se envió una nueva contraseña
        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
        }

        usuarioRepository.save(usuarioExistente);
    }




}
