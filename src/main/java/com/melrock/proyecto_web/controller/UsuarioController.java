package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.UsuarioDTO;
import com.melrock.proyecto_web.dto.UsuarioRegistroDTO;
import com.melrock.proyecto_web.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Crear usuario dentro de una empresa
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioRegistroDTO usuarioRegistroDTO) {
        UsuarioDTO nuevo = usuarioService.registrarUsuario(usuarioRegistroDTO);
        return ResponseEntity.ok(nuevo);
    }

    // Listar usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuariosDTO = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuariosDTO);
    }

    // Buscar usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        UsuarioDTO usuarioDTO = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(usuarioDTO);
    }

    // Buscar usuario por correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioDTO> buscarPorCorreo(@PathVariable String correo) {
        UsuarioDTO usuarioDTO = usuarioService.buscarPorCorreo(correo);
        return ResponseEntity.ok(usuarioDTO);
    }

    // Inicio de sesión
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestParam String correo, @RequestParam String contraseña) {
        UsuarioDTO usuarioDTO = usuarioService.login(correo, contraseña);
        return ResponseEntity.ok(usuarioDTO);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id,
                                                        @Valid @RequestBody UsuarioRegistroDTO usuarioRegistroDTO) {
        UsuarioDTO actualizado = usuarioService.actualizarUsuario(id, usuarioRegistroDTO);
        return ResponseEntity.ok(actualizado);
    }
}
