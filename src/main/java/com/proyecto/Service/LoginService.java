package com.proyecto.Service;

import com.proyecto.Model.Usuario;
import com.proyecto.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correo));

        // Devuelve un objeto User de Spring con email y contraseña encriptada
        return new User(
                usuario.getCorreo(),         // username (correo)
                usuario.getPassword(),       // password (encriptada)
                Collections.emptyList()      // roles vacíos por ahora
        );
    }
}
