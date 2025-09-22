package com.melrock.demo.services;

import com.melrock.demo.dto.DiagramDTO;
import com.melrock.demo.dto.ActivityDTO;
import com.melrock.demo.dto.GatewayDTO;
import com.melrock.demo.dto.EdgeDTO;
import com.melrock.demo.models.Activity;
import com.melrock.demo.models.Gateway;
import com.melrock.demo.models.Edge;
import com.melrock.demo.repositories.ProcessRepository;
import com.melrock.demo.repositories.ActivityRepository;
import com.melrock.demo.repositories.GatewayRepository;
import com.melrock.demo.repositories.EdgeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessService {

    private final ProcessRepository processRepository;
    private final ActivityRepository activityRepository;
    private final GatewayRepository gateawayRepository;
    private final EdgeRepository edgeRepository;

    @Autowired
    public ProcessService(ProcessRepository processRepository, ActivityRepository activityRepository, GatewayRepository gateawayRepository, EdgeRepository edgeRepository) {
        this.processRepository = processRepository;
        this.activityRepository = activityRepository;
        this.gateawayRepository = gateawayRepository;
        this.edgeRepository = edgeRepository;
    }

    @Transactional
    public Process create(Process p) {
        return processRepository.save(p);
    }
    
    @Transactional
    public DiagramDTO getProcessDiagram(Long processId) {
        // 1. Validar que el proceso existe para evitar errores
        processRepository.findById(processId)
            .orElseThrow(() -> new RuntimeException("Process not found with ID: " + processId));

        // 2. Obtener las entidades del proceso (actividades, gateways, edges)
        List<Activity> activities = activityRepository.findByProcessId(processId);
        List<Gateway> gateways = gateawayRepository.findByProcessId(processId);
        List<Edge> edges = edgeRepository.findByProcessId(processId);

        // 3. Convertir las entidades a sus respectivos DTOs
        List<ActivityDTO> activityDTOs = activities.stream().map(this::convertToActivityDto).collect(Collectors.toList());
        List<GatewayDTO> gatewayDTOs = gateways.stream().map(this::convertToGatewayDto).collect(Collectors.toList());
        List<EdgeDTO> edgeDTOs = edges.stream().map(this::convertToEdgeDto).collect(Collectors.toList());

        // 4. Construir y devolver el objeto DiagramDTO
        DiagramDTO diagramDTO = new DiagramDTO();
        diagramDTO.setActivities(activityDTOs);
        diagramDTO.setGateways(gatewayDTOs);
        diagramDTO.setEdges(edgeDTOs);

        return diagramDTO;
    }

    // Métodos de conversión de Entidad a DTO
    private ActivityDTO convertToActivityDto(Activity activity) {
        // Implementación de la conversión de Activity a ActivityDTO
        ActivityDTO dto = new ActivityDTO();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setDescription(activity.getDescription());
        return dto;
    }

    private GatewayDTO convertToGatewayDto(Gateway gateway) {
        // Implementación de la conversión de Gateway a GatewayDTO
        GatewayDTO dto = new GatewayDTO();
        dto.setId(gateway.getId());
        dto.setName(gateway.getName());
        dto.setType(gateway.getType());
        dto.setConditions(gateway.getConditions());
        return dto;
    }

    private EdgeDTO convertToEdgeDto(Edge edge) {
        // Implementación de la conversión de Edge a EdgeDTO
        EdgeDTO dto = new EdgeDTO();
        dto.setId(edge.getId());
        // Lógica para asignar las IDs de origen y destino
        return dto;
    }
}