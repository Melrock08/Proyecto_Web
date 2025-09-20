package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.model.Actividad;
import com.melrock.proyecto_web.service.ActividadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/actividades")
public class ActividadController {

    private final ActividadService actividadService;

    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    // Crear actividad (HU-08)
    @PostMapping
    public ResponseEntity<Actividad> crearActividad(@RequestBody Actividad actividad) {
        Actividad nueva = actividadService.crearActividad(actividad);
        return ResponseEntity.ok(nueva);
    }

    // Editar actividad (HU-09)
    @PutMapping("/{id}")
    public ResponseEntity<Actividad> editarActividad(@PathVariable Long id, @RequestBody Actividad actividad) {
        Actividad actualizada = actividadService.editarActividad(id, actividad);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar actividad (HU-10)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Long id) {
        actividadService.eliminarActividad(id);
        return ResponseEntity.noContent().build();
    }

    // Listar actividades
    @GetMapping
    public ResponseEntity<List<Actividad>> listarActividades() {
        return ResponseEntity.ok(actividadService.listarActividades());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Actividad>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(actividadService.buscarPorId(id));
    }
}