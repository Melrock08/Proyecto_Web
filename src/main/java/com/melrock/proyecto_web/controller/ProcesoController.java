package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.ProcesoDTO;
import com.melrock.proyecto_web.service.ProcesoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procesos")
public class ProcesoController {

    private final ProcesoService procesoService;

    public ProcesoController(ProcesoService procesoService) {
        this.procesoService = procesoService;
    }

    // Crear proceso (HU-04)
    @PostMapping
    public ResponseEntity<ProcesoDTO> crearProceso(@Valid @RequestBody ProcesoDTO dto) {
        ProcesoDTO nuevo = procesoService.crearProceso(dto);
        return ResponseEntity.ok(nuevo);
    }

    // Editar proceso (HU-05)
    @PutMapping("/{id}")
    public ResponseEntity<ProcesoDTO> editarProceso(@PathVariable Long id, @Valid @RequestBody ProcesoDTO dto) {
        ProcesoDTO actualizado = procesoService.editarProceso(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // Desactivar proceso (HU-06)
    @DeleteMapping("/{id}")
    public ResponseEntity<ProcesoDTO> desactivarProceso(@PathVariable Long id) {
        ProcesoDTO inactivo = procesoService.desactivarProceso(id);
        return ResponseEntity.ok(inactivo);
    }

    // Consultar todos los procesos (HU-07)
    @GetMapping
    public ResponseEntity<List<ProcesoDTO>> listarProcesos() {
        List<ProcesoDTO> lista = procesoService.listarProcesos();
        return ResponseEntity.ok(lista);
    }

    // Consultar procesos por empresa (HU-07)
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<ProcesoDTO>> listarPorEmpresa(@PathVariable Long idEmpresa) {
        List<ProcesoDTO> lista = procesoService.listarPorEmpresa(idEmpresa);
        return ResponseEntity.ok(lista);
    }

    // Consultar proceso por ID (HU-07)
    @GetMapping("/{id}")
    public ResponseEntity<ProcesoDTO> buscarPorId(@PathVariable Long id) {
        return procesoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
