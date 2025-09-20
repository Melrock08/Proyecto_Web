package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.model.Rol;
import com.melrock.proyecto_web.service.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    // Crear rol (HU-17)
    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {
        Rol nuevo = rolService.crearRol(rol);
        return ResponseEntity.ok(nuevo);
    }

    // Editar rol (HU-18)
    @PutMapping("/{id}")
    public ResponseEntity<Rol> editarRol(@PathVariable Long id, @RequestBody Rol rol) {
        Rol actualizado = rolService.editarRol(id, rol);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar rol (HU-19)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }

    // Listar roles (HU-20)
    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(rolService.listarRoles());
    }

    // Buscar rol por ID (HU-20)
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Rol>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.buscarPorId(id));
    }
}

