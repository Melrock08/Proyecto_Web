package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.EdgeDTO;
import com.melrock.proyecto_web.service.EdgeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arcos")
public class EdgeController {

    private final EdgeService edgeService;

    public EdgeController(EdgeService edgeService) {
        this.edgeService = edgeService;
    }

    // Crear arco (HU-11)
    @PostMapping
    public ResponseEntity<EdgeDTO> crearEdge(@Valid @RequestBody EdgeDTO edgeDTO) {
        EdgeDTO nuevo = edgeService.crearEdge(edgeDTO);
        return ResponseEntity.ok(nuevo);
    }

    // Editar arco (HU-12)
    @PutMapping("/{id}")
    public ResponseEntity<EdgeDTO> editarEdge(@PathVariable Long id, @Valid @RequestBody EdgeDTO edgeDTO) {
        EdgeDTO actualizado = edgeService.editarEdge(id, edgeDTO);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar arco (HU-13)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEdge(@PathVariable Long id) {
        edgeService.eliminarEdge(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todos los arcos
    @GetMapping
    public ResponseEntity<List<EdgeDTO>> listarEdges() {
        List<EdgeDTO> edges = edgeService.listarEdges();
        return ResponseEntity.ok(edges);
    }

    // Buscar arco por ID
    @GetMapping("/{id}")
    public ResponseEntity<EdgeDTO> buscarPorId(@PathVariable Long id) {
        return edgeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
