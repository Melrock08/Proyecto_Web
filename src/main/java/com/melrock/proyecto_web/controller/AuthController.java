package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.LoginDTO;
import com.melrock.proyecto_web.dto.UsuarioDTO;
import com.melrock.proyecto_web.dto.UsuarioRegistroDTO;
import com.melrock.proyecto_web.dto.EmpresaRegistroDTO;
import com.melrock.proyecto_web.model.Empresa;
import com.melrock.proyecto_web.security.JwtUtil;
import com.melrock.proyecto_web.service.EmpresaService;
import com.melrock.proyecto_web.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final JwtUtil jwtUtil;

    // ---------------------------
    // 🔹 LOGIN
    // ---------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        UsuarioDTO usuario = usuarioService.login(loginDTO.getCorreo(), loginDTO.getContrasena());

        List<SimpleGrantedAuthority> authorities = List.of();
        if (usuario.getRolSistema() != null && !usuario.getRolSistema().isBlank()) {
            authorities = List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRolSistema().toUpperCase()));
        }

        User userDetails = new User(
                usuario.getCorreo(),
                "",
                authorities
        );

        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(usuario, token));
    }

    // ---------------------------
    // 🔹 REGISTRO (usuario + empresa obligatoria en DTO)
    // ---------------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UsuarioRegistroDTO dto) {
        try {
            // 1) crear o encontrar la empresa (EmpresaService usa solo EmpresaRepository)
            EmpresaRegistroDTO eDto = dto.getEmpresa();
            Empresa empresa = empresaService.crearOEncontrarEmpresa(eDto);

            // 2) crear usuario pasándole solo el idEmpresa (UsuarioService usa solo UsuarioRepository)
            UsuarioDTO nuevo = usuarioService.registrarUsuarioConEmpresaId(dto, empresa.getIdEmpresa());

            // 3) generar token y devolver
            List<SimpleGrantedAuthority> authorities = List.of();
            if (nuevo.getRolSistema() != null && !nuevo.getRolSistema().isBlank()) {
                authorities = List.of(new SimpleGrantedAuthority("ROLE_" + nuevo.getRolSistema().toUpperCase()));
            }
            User userDetails = new User(nuevo.getCorreo(), "", authorities);
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(nuevo, token));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error creating user"));
        }
    }

    // ---------------------------
    // Response helper records
    // ---------------------------
    public record AuthResponse(UsuarioDTO usuario, String token) {}

    public record ErrorResponse(String message) {}
}
