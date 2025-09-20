package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.repository.GatewayRepository;
import org.springframework.stereotype.Service;
import com.melrock.proyecto_web.model.Gateway;

import java.util.List;
import java.util.Optional;

@Service
public class GatewayService {

    private final GatewayRepository gatewayRepository;

    public GatewayService(GatewayRepository gatewayRepository) {
        this.gatewayRepository = gatewayRepository;
    }

    // Crear gateway
     public Gateway crearGateway(Gateway gateway) {
        if (gateway.getTipo() == null || gateway.getTipo().isBlank()) {
            throw new RuntimeException("El gateway debe tener un tipo definido");
        }

        String tipo = gateway.getTipo().toUpperCase();
        if (!tipo.equals("EXCLUSIVO") && !tipo.equals("PARALELO") && !tipo.equals("INCLUSIVO")) {
            throw new RuntimeException("Tipo de gateway inválido (válidos: Exclusivo, Paralelo, Inclusivo)");
        }

        gateway.setTipo(tipo);
        return gatewayRepository.save(gateway);
    }

    // Editar gateway (actualizar tipo o condiciones)
    public Gateway editarGateway(Long id, Gateway datosActualizados) {
        Optional<Gateway> existenteOpt = gatewayRepository.findById(id);

        if (existenteOpt.isEmpty()) {
            throw new RuntimeException("Gateway no encontrado");
        }

        Gateway existente = existenteOpt.get();
        existente.setTipo(datosActualizados.getTipo().toUpperCase());

        return gatewayRepository.save(existente);
    }

    // Listar todos los gateways
    public List<Gateway> listarGateways() {
        return gatewayRepository.findAll();
    }

    // Buscar gateway por id
    public Optional<Gateway> buscarPorId(Long id) {
        return gatewayRepository.findById(id);
    }

    // Eliminar gateway
    public void eliminarGateway(Long id) {
        Optional<Gateway> existenteOpt = gatewayRepository.findById(id);

        if (existenteOpt.isEmpty()) {
            throw new RuntimeException("Gateway no encontrado");
        }

        gatewayRepository.deleteById(id);
    }
}
