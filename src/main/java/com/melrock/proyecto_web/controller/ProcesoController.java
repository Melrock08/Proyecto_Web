package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.model.Proceso;
import com.melrock.proyecto_web.service.ProcesoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/procesos")
public class ProcesoController {

    private final ProcesoService procesoService;

    public ProcesoController(ProcesoService procesoService) {
        this.procesoService = procesoService;
    }

    // Crear proceso (HU-04)
    @PostMapping
    public ResponseEntity<Proceso> crearProceso(@RequestBody Proceso proceso) {
        Proceso nuevo = procesoService.crearProceso(proceso);
        return ResponseEntity.ok(nuevo);
    }

    // Editar proceso (HU-05)
    @PutMapping("/{id}")
    public ResponseEntity<Proceso> editarProceso(@PathVariable Long id, @RequestBody Proceso proceso) {
        Proceso actualizado = procesoService.editarProceso(id, proceso);
        return ResponseEntity.ok(actualizado);
    }

    // Desactivar proceso (HU-06)
    @DeleteMapping("/{id}")
    public ResponseEntity<Proceso> desactivarProceso(@PathVariable Long id) {
        Proceso inactivo = procesoService.desactivarProceso(id);
        return ResponseEntity.ok(inactivo);
    }

    // Consultar todos (HU-07)
    @GetMapping
    public ResponseEntity<List<Proceso>> listarProcesos() {
        return ResponseEntity.ok(procesoService.listarProcesos());
    }

    // Consultar por empresa (HU-07)
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<Proceso>> listarPorEmpresa(@PathVariable Long idEmpresa) {
        return ResponseEntity.ok(procesoService.listarPorEmpresa(idEmpresa));
    }

    // Consultar por ID (HU-07)
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Proceso>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(procesoService.buscarPorId(id));
    }
}
