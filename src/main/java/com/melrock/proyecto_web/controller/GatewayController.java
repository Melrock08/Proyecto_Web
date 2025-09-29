package com.melrock.proyecto_web.controller;

import com.melrock.proyecto_web.dto.GatewayDTO;
import com.melrock.proyecto_web.model.Gateway;
import com.melrock.proyecto_web.service.GatewayService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gateways")
public class GatewayController {

    private final GatewayService gatewayService;
    private final ModelMapper modelMapper;

    public GatewayController(GatewayService gatewayService, ModelMapper modelMapper) {
        this.gatewayService = gatewayService;
        this.modelMapper = modelMapper;
    }

    // Crear gateway
    @PostMapping
    public ResponseEntity<GatewayDTO> crearGateway(@Valid @RequestBody GatewayDTO gatewayDTO) {
        Gateway gateway = modelMapper.map(gatewayDTO, Gateway.class);
        Gateway nuevo = gatewayService.crearGateway(gateway);
        return ResponseEntity.ok(modelMapper.map(nuevo, GatewayDTO.class));
    }

    // Editar gateway
    @PutMapping("/{id}")
    public ResponseEntity<GatewayDTO> editarGateway(@PathVariable Long id,
                                                    @Valid @RequestBody GatewayDTO gatewayDTO) {
        Gateway gateway = modelMapper.map(gatewayDTO, Gateway.class);
        Gateway actualizado = gatewayService.editarGateway(id, gateway);
        return ResponseEntity.ok(modelMapper.map(actualizado, GatewayDTO.class));
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
        List<GatewayDTO> gateways = gatewayService.listarGateways()
                .stream()
                .map(g -> modelMapper.map(g, GatewayDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(gateways);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<GatewayDTO> buscarPorId(@PathVariable Long id) {
        return gatewayService.buscarPorId(id)
                .map(g -> ResponseEntity.ok(modelMapper.map(g, GatewayDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }
}
