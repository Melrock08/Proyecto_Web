package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.RolDTO;
import com.melrock.proyecto_web.service.RolService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }
    
    // Crear rol
    @PostMapping
    public ResponseEntity<RolDTO> crearRol(@Valid @RequestBody RolDTO rolDTO) {
        RolDTO nuevo = rolService.crearRol(rolDTO);
        return ResponseEntity.ok(nuevo);
    }

    // Editar rol
    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> editarRol(@PathVariable Long id, @Valid @RequestBody RolDTO rolDTO) {
        RolDTO actualizado = rolService.editarRol(id, rolDTO);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar rol
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }

    // Listar roles
    @GetMapping
    public ResponseEntity<List<RolDTO>> listarRoles() {
        List<RolDTO> roles = rolService.listarRoles();
        return ResponseEntity.ok(roles);
    }

    // Buscar rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> buscarPorId(@PathVariable Long id) {
        RolDTO rol = rolService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return ResponseEntity.ok(rol);
    }
}
