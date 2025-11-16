package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.dto.ActividadDTO;
import com.melrock.proyecto_web.dto.EdgeDTO;
import com.melrock.proyecto_web.dto.GatewayDTO;
import com.melrock.proyecto_web.dto.ProcesoDTO;
import com.melrock.proyecto_web.model.Actividad;
import com.melrock.proyecto_web.model.Edge;
import com.melrock.proyecto_web.model.Gateway;
import com.melrock.proyecto_web.model.Proceso;
import com.melrock.proyecto_web.repository.EdgeRepository;
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
    private final ProcesoService procesoService;
    private final ActividadService actividadService;
    private final GatewayService gatewayService;
    private final ModelMapper modelMapper;

    // Crear arco
    public EdgeDTO crearEdge(EdgeDTO dto) {
        Edge edge = new Edge();

        ProcesoDTO procesoDto = procesoService.buscarPorId(dto.getIdProceso())
                .orElseThrow(() -> new RuntimeException("Proceso no encontrado"));
        Proceso proceso = modelMapper.map(procesoDto, Proceso.class);
        edge.setProceso(proceso);

        if (dto.getIdOrigenActividad() != null) {
            ActividadDTO origenActDto = actividadService.buscarPorId(dto.getIdOrigenActividad())
                    .orElseThrow(() -> new RuntimeException("Actividad origen no encontrada"));
            Actividad origenAct = modelMapper.map(origenActDto, Actividad.class);
            edge.setOrigenActividad(origenAct);
        }
        if (dto.getIdDestinoActividad() != null) {
            ActividadDTO destinoActDto = actividadService.buscarPorId(dto.getIdDestinoActividad())
                    .orElseThrow(() -> new RuntimeException("Actividad destino no encontrada"));
            Actividad destinoAct = modelMapper.map(destinoActDto, Actividad.class);
            edge.setDestinoActividad(destinoAct);
        }
        if (dto.getIdOrigenGateway() != null) {
            GatewayDTO origenGtwDto = gatewayService.buscarPorId(dto.getIdOrigenGateway())
                    .orElseThrow(() -> new RuntimeException("Gateway origen no encontrado"));
            Gateway origenGtw = modelMapper.map(origenGtwDto, Gateway.class);
            edge.setOrigenGateway(origenGtw);
        }
        if (dto.getIdDestinoGateway() != null) {
            GatewayDTO destinoGtwDto = gatewayService.buscarPorId(dto.getIdDestinoGateway())
                    .orElseThrow(() -> new RuntimeException("Gateway destino no encontrado"));
            Gateway destinoGtw = modelMapper.map(destinoGtwDto, Gateway.class);
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
            ActividadDTO origenActDto = actividadService.buscarPorId(dto.getIdOrigenActividad())
                    .orElseThrow(() -> new RuntimeException("Actividad origen no encontrada"));
            Actividad origenAct = modelMapper.map(origenActDto, Actividad.class);
            existente.setOrigenActividad(origenAct);
        } else {
            existente.setOrigenActividad(null);
        }

        if (dto.getIdDestinoActividad() != null) {
            ActividadDTO destinoActDto = actividadService.buscarPorId(dto.getIdDestinoActividad())
                    .orElseThrow(() -> new RuntimeException("Actividad destino no encontrada"));
            Actividad destinoAct = modelMapper.map(destinoActDto, Actividad.class);
            existente.setDestinoActividad(destinoAct);
        } else {
            existente.setDestinoActividad(null);
        }

        if (dto.getIdOrigenGateway() != null) {
            GatewayDTO origenGtwDto = gatewayService.buscarPorId(dto.getIdOrigenGateway())
                    .orElseThrow(() -> new RuntimeException("Gateway origen no encontrado"));
            Gateway origenGtw = modelMapper.map(origenGtwDto, Gateway.class);
            existente.setOrigenGateway(origenGtw);
        } else {
            existente.setOrigenGateway(null);
        }

        if (dto.getIdDestinoGateway() != null) {
            GatewayDTO destinoGtwDto = gatewayService.buscarPorId(dto.getIdDestinoGateway())
                    .orElseThrow(() -> new RuntimeException("Gateway destino no encontrado"));
            Gateway destinoGtw = modelMapper.map(destinoGtwDto, Gateway.class);
            existente.setDestinoGateway(destinoGtw);
        } else {
            existente.setDestinoGateway(null);
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
        if (edge.getProceso() != null) dto.setIdProceso(edge.getProceso().getIdProceso());
        if (edge.getOrigenActividad() != null) dto.setIdOrigenActividad(edge.getOrigenActividad().getIdActividad());
        if (edge.getDestinoActividad() != null) dto.setIdDestinoActividad(edge.getDestinoActividad().getIdActividad());
        if (edge.getOrigenGateway() != null) dto.setIdOrigenGateway(edge.getOrigenGateway().getIdGateway());
        if (edge.getDestinoGateway() != null) dto.setIdDestinoGateway(edge.getDestinoGateway().getIdGateway());
        return dto;
    }
}
