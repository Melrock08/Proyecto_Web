package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.EmpresaDTO;
import com.melrock.proyecto_web.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")

public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    // Crear empresa
    @PostMapping
    public ResponseEntity<EmpresaDTO> crearEmpresa(
            @Valid @RequestBody EmpresaDTO empresaDTO) {

        EmpresaDTO nueva = empresaService.crearEmpresa(empresaDTO);
        return ResponseEntity.ok(nueva);
    }


    // Listar empresas
    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> listarEmpresas() {
        return ResponseEntity.ok(empresaService.listarEmpresas());
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> buscarPorId(@PathVariable Long id) {
        return empresaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por NIT
    @GetMapping("/nit/{nit}")
    public ResponseEntity<EmpresaDTO> buscarPorNit(@PathVariable String nit) {
        EmpresaDTO empresa = empresaService.buscarPorNit(nit);
        return ResponseEntity.ok(empresa);
    }

    // Eliminar empresa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        empresaService.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar empresa
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> actualizarEmpresa(
            @PathVariable Long id,
            @Valid @RequestBody EmpresaDTO empresaDTO) {

        EmpresaDTO actualizado = empresaService.actualizarEmpresa(id, empresaDTO);
        return ResponseEntity.ok(actualizado);
    }
}

