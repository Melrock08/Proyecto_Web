package com.melrock.proyecto_web.security;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.melrock.proyecto_web.model.Usuario;
import com.melrock.proyecto_web.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + correo);
        }

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        return new User(
                usuario.getCorreo(),
                usuario.getContrasena(), // debe estar BCrypt-hashed en BD
                Collections.singletonList(authority)
        );
    }
}
