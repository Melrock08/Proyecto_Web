package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.LoginDTO;
import com.melrock.proyecto_web.dto.UsuarioDTO;
import com.melrock.proyecto_web.dto.UsuarioRegistroDTO;
import com.melrock.proyecto_web.security.JwtUtil;
import com.melrock.proyecto_web.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    // ---------------------------
    // 🔹 LOGIN
    // ---------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        UsuarioDTO usuario = usuarioService.login(loginDTO.getCorreo(), loginDTO.getContrasena());

        // Crear UserDetails
        User userDetails = new User(
                usuario.getCorreo(),
                "",
                java.util.List.of() // por ahora sin roles
        );

        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(usuario, token));
    }

    // ---------------------------
    // 🔹 REGISTRO
    // ---------------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioRegistroDTO dto) {
        UsuarioDTO nuevo = usuarioService.registrarUsuario(dto);
        return ResponseEntity.ok(nuevo);
    }

    public record AuthResponse(UsuarioDTO usuario, String token) {}
}
