package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.ActividadDTO;
import com.melrock.proyecto_web.service.ActividadService;
import jakarta.validation.Valid;
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

    // Crear actividad
    @PostMapping
    public ResponseEntity<ActividadDTO> crearActividad(@Valid @RequestBody ActividadDTO actividadDTO) {
        ActividadDTO nueva = actividadService.crearActividad(actividadDTO);
        return ResponseEntity.ok(nueva);
    }

    // Editar actividad
    @PutMapping("/{id}")
    public ResponseEntity<ActividadDTO> editarActividad(@PathVariable Long id, @Valid @RequestBody ActividadDTO actividadDTO) {
        ActividadDTO actualizada = actividadService.editarActividad(id, actividadDTO);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar actividad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Long id) {
        actividadService.eliminarActividad(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todas las actividades
    @GetMapping
    public ResponseEntity<List<ActividadDTO>> listarActividades() {
        List<ActividadDTO> actividades = actividadService.listarActividades();
        return ResponseEntity.ok(actividades);
    }

    // Buscar actividad por ID
    @GetMapping("/{id}")
    public ResponseEntity<ActividadDTO> buscarPorId(@PathVariable Long id) {
        Optional<ActividadDTO> actividadOpt = actividadService.buscarPorId(id);
        return actividadOpt.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
}

