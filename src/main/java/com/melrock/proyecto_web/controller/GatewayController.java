package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.model.Gateway;
import com.melrock.proyecto_web.service.GatewayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gateways")
public class GatewayController {

    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    // Crear gateway (HU-14)
    @PostMapping
    public ResponseEntity<Gateway> crearGateway(@RequestBody Gateway gateway) {
        Gateway nuevo = gatewayService.crearGateway(gateway);
        return ResponseEntity.ok(nuevo);
    }

    // Editar gateway (HU-15)
    @PutMapping("/{id}")
    public ResponseEntity<Gateway> editarGateway(@PathVariable Long id, @RequestBody Gateway gateway) {
        Gateway actualizado = gatewayService.editarGateway(id, gateway);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar gateway (HU-16)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGateway(@PathVariable Long id) {
        gatewayService.eliminarGateway(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Gateway>> listarGateways() {
        return ResponseEntity.ok(gatewayService.listarGateways());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Gateway>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(gatewayService.buscarPorId(id));
    }
}
