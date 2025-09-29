package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.model.Proceso;
import com.melrock.proyecto_web.dto.EmpresaDTO;
import com.melrock.proyecto_web.service.ProcesoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/procesos")
public class ProcesoController {

    private final ProcesoService procesoService;
    private final ModelMapper modelMapper;

    public ProcesoController(ProcesoService procesoService, ModelMapper modelMapper) {
        this.procesoService = procesoService;
        this.modelMapper = modelMapper;
    }

    // Crear proceso (HU-04)
    @PostMapping
    public ResponseEntity<Proceso> crearProceso(@Valid @RequestBody Proceso proceso) {
        Proceso nuevo = procesoService.crearProceso(proceso);
        return ResponseEntity.ok(nuevo);
    }

    // Editar proceso (HU-05)
    @PutMapping("/{id}")
    public ResponseEntity<Proceso> editarProceso(@PathVariable Long id, @Valid @RequestBody Proceso proceso) {
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
    public ResponseEntity<List<EmpresaDTO>> listarPorEmpresa(@PathVariable Long idEmpresa) {
        List<Proceso> procesos = procesoService.listarPorEmpresa(idEmpresa);

        // Mapeamos solo la Empresa asociada de cada proceso
        List<EmpresaDTO> empresas = procesos.stream()
                .map(p -> modelMapper.map(p.getEmpresa(), EmpresaDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(empresas);
    }

    // Consultar por ID (HU-07)
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Proceso>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(procesoService.buscarPorId(id));
    }
}
