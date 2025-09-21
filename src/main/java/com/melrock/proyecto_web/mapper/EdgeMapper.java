package com.melrock.proyecto_web.mapper;

import com.melrock.proyecto_web.dto.EdgeDTO;
import com.melrock.proyecto_web.model.Edge;

public class EdgeMapper {

    public static EdgeDTO toDTO(Edge edge) {
        EdgeDTO dto = new EdgeDTO();
        dto.setIdEdge(edge.getIdEdge());
        dto.setIdProceso(edge.getProceso() != null ? edge.getProceso().getIdProceso() : null);
        dto.setIdOrigenActividad(edge.getOrigenActividad() != null ? edge.getOrigenActividad().getIdActividad() : null);
        dto.setIdOrigenGateway(edge.getOrigenGateway() != null ? edge.getOrigenGateway().getIdGateway() : null);
        dto.setIdDestinoActividad(edge.getDestinoActividad() != null ? edge.getDestinoActividad().getIdActividad() : null);
        dto.setIdDestinoGateway(edge.getDestinoGateway() != null ? edge.getDestinoGateway().getIdGateway() : null);
        return dto;
    }

    public static Edge toEntity(EdgeDTO dto) {
        Edge edge = new Edge();
        edge.setIdEdge(dto.getIdEdge());
        return edge;
    }
}
