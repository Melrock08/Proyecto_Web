package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.GatewayDTO;
import com.melrock.proyecto_web.service.GatewayService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gateways")
public class GatewayController {

    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    // Crear gateway
    @PostMapping
    public ResponseEntity<GatewayDTO> crearGateway(@Valid @RequestBody GatewayDTO gatewayDTO) {
        GatewayDTO nuevo = gatewayService.crearGateway(gatewayDTO);
        return ResponseEntity.ok(nuevo);
    }

    // Editar gateway
    @PutMapping("/{id}")
    public ResponseEntity<GatewayDTO> editarGateway(@PathVariable Long id,
                                                    @Valid @RequestBody GatewayDTO gatewayDTO) {
        GatewayDTO actualizado = gatewayService.editarGateway(id, gatewayDTO);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar gateway
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGateway(@PathVariable Long id) {
        gatewayService.eliminarGateway(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<GatewayDTO>> listarGateways() {
        List<GatewayDTO> gateways = gatewayService.listarGateways();
        return ResponseEntity.ok(gateways);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<GatewayDTO> buscarPorId(@PathVariable Long id) {
        return gatewayService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
