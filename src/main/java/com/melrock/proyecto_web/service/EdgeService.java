package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.dto.EdgeDTO;
import com.melrock.proyecto_web.model.Actividad;
import com.melrock.proyecto_web.model.Edge;
import com.melrock.proyecto_web.model.Gateway;
import com.melrock.proyecto_web.model.Proceso;
import com.melrock.proyecto_web.repository.ActividadRepository;
import com.melrock.proyecto_web.repository.EdgeRepository;
import com.melrock.proyecto_web.repository.GatewayRepository;
import com.melrock.proyecto_web.repository.ProcesoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EdgeService {

    private final EdgeRepository edgeRepository;
    private final ProcesoRepository procesoRepository;
    private final ActividadRepository actividadRepository;
    private final GatewayRepository gatewayRepository;
    private final ModelMapper modelMapper;

    
    // Crear o actualizar arco
    public EdgeDTO crearEdge(EdgeDTO dto) {
        Edge edge = new Edge();

        Proceso proceso = procesoRepository.findById(dto.getIdProceso())
                .orElseThrow(() -> new RuntimeException("Proceso no encontrado"));
        edge.setProceso(proceso);

        if (dto.getIdOrigenActividad() != null) {
            Actividad origenAct = actividadRepository.findById(dto.getIdOrigenActividad())
                    .orElseThrow(() -> new RuntimeException("Actividad origen no encontrada"));
            edge.setOrigenActividad(origenAct);
        }
        if (dto.getIdDestinoActividad() != null) {
            Actividad destinoAct = actividadRepository.findById(dto.getIdDestinoActividad())
                    .orElseThrow(() -> new RuntimeException("Actividad destino no encontrada"));
            edge.setDestinoActividad(destinoAct);
        }
        if (dto.getIdOrigenGateway() != null) {
            Gateway origenGtw = gatewayRepository.findById(dto.getIdOrigenGateway())
                    .orElseThrow(() -> new RuntimeException("Gateway origen no encontrado"));
            edge.setOrigenGateway(origenGtw);
        }
        if (dto.getIdDestinoGateway() != null) {
            Gateway destinoGtw = gatewayRepository.findById(dto.getIdDestinoGateway())
                    .orElseThrow(() -> new RuntimeException("Gateway destino no encontrado"));
            edge.setDestinoGateway(destinoGtw);
        }

        Edge guardado = edgeRepository.save(edge);
        return convertirADTO(guardado);
    }

    // Editar arco
    public EdgeDTO editarEdge(Long id, EdgeDTO dto) {
        Edge existente = edgeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arco no encontrado"));

        if (dto.getIdOrigenActividad() != null) {
            Actividad origenAct = actividadRepository.findById(dto.getIdOrigenActividad())
                    .orElseThrow(() -> new RuntimeException("Actividad origen no encontrada"));
            existente.setOrigenActividad(origenAct);
        }
        if (dto.getIdDestinoActividad() != null) {
            Actividad destinoAct = actividadRepository.findById(dto.getIdDestinoActividad())
                    .orElseThrow(() -> new RuntimeException("Actividad destino no encontrada"));
            existente.setDestinoActividad(destinoAct);
        }
        if (dto.getIdOrigenGateway() != null) {
            Gateway origenGtw = gatewayRepository.findById(dto.getIdOrigenGateway())
                    .orElseThrow(() -> new RuntimeException("Gateway origen no encontrado"));
            existente.setOrigenGateway(origenGtw);
        }
        if (dto.getIdDestinoGateway() != null) {
            Gateway destinoGtw = gatewayRepository.findById(dto.getIdDestinoGateway())
                    .orElseThrow(() -> new RuntimeException("Gateway destino no encontrado"));
            existente.setDestinoGateway(destinoGtw);
        }

        Edge actualizado = edgeRepository.save(existente);
        return convertirADTO(actualizado);
    }

    // Listar todos
    public List<EdgeDTO> listarEdges() {
        return edgeRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar por id
    public Optional<EdgeDTO> buscarPorId(Long id) {
        return edgeRepository.findById(id).map(this::convertirADTO);
    }

    // Eliminar arco
    public void eliminarEdge(Long id) {
        if (!edgeRepository.existsById(id)) {
            throw new RuntimeException("Arco no encontrado");
        }
        edgeRepository.deleteById(id);
    }

    // ===== Método auxiliar =====
    private EdgeDTO convertirADTO(Edge edge) {
        EdgeDTO dto = new EdgeDTO();
        dto.setIdEdge(edge.getIdEdge());
        dto.setIdProceso(edge.getProceso().getIdProceso());
        if (edge.getOrigenActividad() != null) dto.setIdOrigenActividad(edge.getOrigenActividad().getIdActividad());
        if (edge.getDestinoActividad() != null) dto.setIdDestinoActividad(edge.getDestinoActividad().getIdActividad());
        if (edge.getOrigenGateway() != null) dto.setIdOrigenGateway(edge.getOrigenGateway().getIdGateway());
        if (edge.getDestinoGateway() != null) dto.setIdDestinoGateway(edge.getDestinoGateway().getIdGateway());
        return dto;
    }
}
