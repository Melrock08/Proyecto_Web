package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.model.Edge;
import com.melrock.proyecto_web.service.EdgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/arcos")
public class EdgeController {

    private final EdgeService edgeService;

    public EdgeController(EdgeService edgeService) {
        this.edgeService = edgeService;
    }

    // Crear arco (HU-11)
    @PostMapping
    public ResponseEntity<Edge> crearEdge(@RequestBody Edge edge) {
        Edge nuevo = edgeService.crearEdge(edge);
        return ResponseEntity.ok(nuevo);
    }

    // Editar arco (HU-12)
    @PutMapping("/{id}")
    public ResponseEntity<Edge> editarEdge(@PathVariable Long id, @RequestBody Edge edge) {
        Edge actualizado = edgeService.editarEdge(id, edge);
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
    public ResponseEntity<List<Edge>> listarEdges() {
        return ResponseEntity.ok(edgeService.listarEdges());
    }

    // Buscar arco por ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Edge>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(edgeService.buscarPorId(id));
    }
}
