package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.model.Empresa;
import com.melrock.proyecto_web.service.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    // Crear empresa con usuario admin inicial
    @PostMapping
    public ResponseEntity<Empresa> crearEmpresa(
            @RequestBody Empresa empresa,
            @RequestParam String correoAdmin,
            @RequestParam String contraseñaAdmin) {
        Empresa nueva = empresaService.registrarEmpresa(empresa, correoAdmin, contraseñaAdmin);
        return ResponseEntity.ok(nueva);
    }

    // Listar empresas
    @GetMapping
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        return ResponseEntity.ok(empresaService.listarEmpresas());
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Empresa>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.buscarPorId(id));
    }

    // Buscar por NIT
    @GetMapping("/nit/{nit}")
    public ResponseEntity<Empresa> buscarPorNit(@PathVariable String nit) {
        return ResponseEntity.ok(empresaService.buscarPorNit(nit));
    }

    // Eliminar empresa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        empresaService.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}