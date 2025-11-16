package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.dto.GatewayDTO;
import com.melrock.proyecto_web.dto.ProcesoDTO;
import com.melrock.proyecto_web.model.Gateway;
import com.melrock.proyecto_web.model.Proceso;
import com.melrock.proyecto_web.repository.GatewayRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GatewayService {

    private final GatewayRepository gatewayRepository;
    private final ProcesoService procesoService;
    private final ModelMapper modelMapper;

    // Crear gateway
    public GatewayDTO crearGateway(GatewayDTO dto) {
        Gateway gateway = new Gateway();

        // Validar tipo
        String tipo = dto.getTipo().toUpperCase();
        if (!tipo.equals("EXCLUSIVO") && !tipo.equals("PARALELO") && !tipo.equals("INCLUSIVO")) {
            throw new RuntimeException("Tipo de gateway inválido (válidos: Exclusivo, Paralelo, Inclusivo)");
        }
        gateway.setTipo(tipo);

        // Vincular proceso via ProcesoService
        ProcesoDTO procesoDto = procesoService.buscarPorId(dto.getIdProceso())
                .orElseThrow(() -> new RuntimeException("Proceso no encontrado"));
        Proceso proceso = modelMapper.map(procesoDto, Proceso.class);
        gateway.setProceso(proceso);

        Gateway guardado = gatewayRepository.save(gateway);
        return convertirADTO(guardado);
    }

    // Editar gateway
    public GatewayDTO editarGateway(Long id, GatewayDTO dto) {
        Gateway existente = gatewayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gateway no encontrado"));

        String tipo = dto.getTipo().toUpperCase();
        if (!tipo.equals("EXCLUSIVO") && !tipo.equals("PARALELO") && !tipo.equals("INCLUSIVO")) {
            throw new RuntimeException("Tipo de gateway inválido (válidos: Exclusivo, Paralelo, Inclusivo)");
        }
        existente.setTipo(tipo);

        if (dto.getIdProceso() != null) {
            ProcesoDTO procesoDto = procesoService.buscarPorId(dto.getIdProceso())
                    .orElseThrow(() -> new RuntimeException("Proceso no encontrado"));
            Proceso proceso = modelMapper.map(procesoDto, Proceso.class);
            existente.setProceso(proceso);
        }

        Gateway actualizado = gatewayRepository.save(existente);
        return convertirADTO(actualizado);
    }

    // Listar todos
    public List<GatewayDTO> listarGateways() {
        return gatewayRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar por id
    public Optional<GatewayDTO> buscarPorId(Long id) {
        return gatewayRepository.findById(id).map(this::convertirADTO);
    }

    // Eliminar gateway
    public void eliminarGateway(Long id) {
        if (!gatewayRepository.existsById(id)) {
            throw new RuntimeException("Gateway no encontrado");
        }
        gatewayRepository.deleteById(id);
    }

    // ===== Método auxiliar =====
    private GatewayDTO convertirADTO(Gateway gateway) {
        GatewayDTO dto = modelMapper.map(gateway, GatewayDTO.class);
        if (gateway.getProceso() != null) {
            dto.setIdProceso(gateway.getProceso().getIdProceso());
        }
        return dto;
    }
}
