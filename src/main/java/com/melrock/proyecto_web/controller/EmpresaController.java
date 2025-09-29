package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.EmpresaDTO;
import com.melrock.proyecto_web.model.Empresa;
import com.melrock.proyecto_web.service.EmpresaService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final ModelMapper modelMapper;

    public EmpresaController(EmpresaService empresaService, ModelMapper modelMapper) {
        this.empresaService = empresaService;
        this.modelMapper = modelMapper;
    }

    // Crear empresa
    @PostMapping
    public ResponseEntity<EmpresaDTO> crearEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) {
        Empresa empresa = modelMapper.map(empresaDTO, Empresa.class);
        Empresa nueva = empresaService.registrarEmpresa(empresa, null, null);
        return ResponseEntity.ok(modelMapper.map(nueva, EmpresaDTO.class));
    }

    // Listar empresas
    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> listarEmpresas() {
        List<EmpresaDTO> empresas = empresaService.listarEmpresas()
                .stream()
                .map(emp -> modelMapper.map(emp, EmpresaDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(empresas);
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> buscarPorId(@PathVariable Long id) {
        return empresaService.buscarPorId(id)
                .map(emp -> ResponseEntity.ok(modelMapper.map(emp, EmpresaDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por NIT
    @GetMapping("/nit/{nit}")
    public ResponseEntity<EmpresaDTO> buscarPorNit(@PathVariable String nit) {
        Empresa empresa = empresaService.buscarPorNit(nit);
        return ResponseEntity.ok(modelMapper.map(empresa, EmpresaDTO.class));
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

        Empresa empresa = modelMapper.map(empresaDTO, Empresa.class);
        Empresa actualizado = empresaService.actualizarEmpresa(id, empresa);
        return ResponseEntity.ok(modelMapper.map(actualizado, EmpresaDTO.class));
    }
}
