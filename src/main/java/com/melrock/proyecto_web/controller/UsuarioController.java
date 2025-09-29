package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.UsuarioDTO;
import com.melrock.proyecto_web.model.Usuario;
import com.melrock.proyecto_web.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.modelmapper.ModelMapper;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    public UsuarioController(UsuarioService usuarioService, ModelMapper modelMapper) {
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
    }

    // Crear usuario dentro de una empresa
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        Usuario nuevo = usuarioService.registrarUsuario(usuario);
        UsuarioDTO response = modelMapper.map(nuevo, UsuarioDTO.class);
        return ResponseEntity.ok(response);
    }

    // Listar usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuariosDTO = usuarioService.listarUsuarios().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .toList();
        return ResponseEntity.ok(usuariosDTO);
    }

    // Buscar usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        return ResponseEntity.ok(usuarioDTO);
    }

    // Buscar usuario por correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioDTO> buscarPorCorreo(@PathVariable String correo) {
        Usuario usuario = usuarioService.buscarPorCorreo(correo);
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        return ResponseEntity.ok(usuarioDTO);
    }

    // Inicio de sesión
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestParam String correo, @RequestParam String contraseña) {
        Usuario usuario = usuarioService.login(correo, contraseña);
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
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
                                                        @Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioDetails = modelMapper.map(usuarioDTO, Usuario.class);
        Usuario actualizado = usuarioService.ActualizarUsuario(id, usuarioDetails);
        UsuarioDTO response = modelMapper.map(actualizado, UsuarioDTO.class);
        return ResponseEntity.ok(response);
    }
}
