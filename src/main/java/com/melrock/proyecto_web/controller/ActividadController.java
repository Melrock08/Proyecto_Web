package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.ActividadDTO;
import com.melrock.proyecto_web.model.Actividad;
import com.melrock.proyecto_web.service.ActividadService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/actividades")
public class ActividadController {

    private final ActividadService actividadService;
    private final ModelMapper modelMapper;

    public ActividadController(ActividadService actividadService, ModelMapper modelMapper) {
        this.actividadService = actividadService;
        this.modelMapper = modelMapper;
    }

    // Crear actividad (HU-08)
    @PostMapping
    public ResponseEntity<ActividadDTO> crearActividad(@Valid @RequestBody ActividadDTO actividadDTO) {
        Actividad actividad = modelMapper.map(actividadDTO, Actividad.class);
        Actividad nueva = actividadService.crearActividad(actividad);
        return ResponseEntity.ok(modelMapper.map(nueva, ActividadDTO.class));
    }

    // Editar actividad (HU-09)
    @PutMapping("/{id}")
    public ResponseEntity<ActividadDTO> editarActividad(@PathVariable Long id, @Valid @RequestBody ActividadDTO actividadDTO) {
        Actividad actividad = modelMapper.map(actividadDTO, Actividad.class);
        Actividad actualizada = actividadService.editarActividad(id, actividad);
        return ResponseEntity.ok(modelMapper.map(actualizada, ActividadDTO.class));
    }

    // Eliminar actividad (HU-10)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Long id) {
        actividadService.eliminarActividad(id);
        return ResponseEntity.noContent().build();
    }

    // Listar actividades
    @GetMapping
    public ResponseEntity<List<ActividadDTO>> listarActividades() {
        List<ActividadDTO> actividades = actividadService.listarActividades()
                .stream()
                .map(act -> modelMapper.map(act, ActividadDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(actividades);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ActividadDTO> buscarPorId(@PathVariable Long id) {
        Optional<Actividad> actividadOpt = actividadService.buscarPorId(id);
        return actividadOpt.map(act -> ResponseEntity.ok(modelMapper.map(act, ActividadDTO.class)))
                           .orElse(ResponseEntity.notFound().build());
    }
}
