package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.EdgeDTO;
import com.melrock.proyecto_web.model.Edge;
import com.melrock.proyecto_web.service.EdgeService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/arcos")
public class EdgeController {

    private final EdgeService edgeService;
    private final ModelMapper modelMapper;

    public EdgeController(EdgeService edgeService, ModelMapper modelMapper) {
        this.edgeService = edgeService;
        this.modelMapper = modelMapper;
    }

    // Crear arco (HU-11)
    @PostMapping
    public ResponseEntity<EdgeDTO> crearEdge(@Valid @RequestBody EdgeDTO edgeDTO) {
        Edge edge = modelMapper.map(edgeDTO, Edge.class);
        Edge nuevo = edgeService.crearEdge(edge);
        return ResponseEntity.ok(modelMapper.map(nuevo, EdgeDTO.class));
    }

    // Editar arco (HU-12)
    @PutMapping("/{id}")
    public ResponseEntity<EdgeDTO> editarEdge(@PathVariable Long id, @Valid @RequestBody EdgeDTO edgeDTO) {
        Edge edge = modelMapper.map(edgeDTO, Edge.class);
        Edge actualizado = edgeService.editarEdge(id, edge);
        return ResponseEntity.ok(modelMapper.map(actualizado, EdgeDTO.class));
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
        List<EdgeDTO> edges = edgeService.listarEdges()
                .stream()
                .map(edge -> modelMapper.map(edge, EdgeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(edges);
    }

    // Buscar arco por ID
    @GetMapping("/{id}")
    public ResponseEntity<EdgeDTO> buscarPorId(@PathVariable Long id) {
        Optional<Edge> edge = edgeService.buscarPorId(id);
        return edge.map(value -> ResponseEntity.ok(modelMapper.map(value, EdgeDTO.class)))
                   .orElse(ResponseEntity.notFound().build());
    }
}
