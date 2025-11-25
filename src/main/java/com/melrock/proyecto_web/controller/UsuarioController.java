package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.UsuarioDTO;
import com.melrock.proyecto_web.dto.UsuarioRegistroDTO;
import com.melrock.proyecto_web.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // 🔹 Crear usuario ADMIN
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioRegistroDTO dto) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(dto));
    }

    // 🔹 Listar usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // 🔹 Buscar usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(
            usuarioService.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
        );
    }

    // 🔹 Buscar usuario por correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioDTO> buscarPorCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(usuarioService.buscarPorCorreo(correo));
    }

    // 🔹 Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
