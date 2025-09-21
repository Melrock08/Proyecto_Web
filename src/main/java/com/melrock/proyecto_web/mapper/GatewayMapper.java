package com.melrock.proyecto_web.mapper;

import com.melrock.proyecto_web.dto.GatewayDTO;
import com.melrock.proyecto_web.model.Gateway;

public class GatewayMapper {

    public static GatewayDTO toDTO(Gateway gateway) {
        GatewayDTO dto = new GatewayDTO();
        dto.setIdGateway(gateway.getIdGateway());
        dto.setTipo(gateway.getTipo());
        dto.setIdProceso(gateway.getProceso() != null ? gateway.getProceso().getIdProceso() : null);
        return dto;
    }

    public static Gateway toEntity(GatewayDTO dto) {
        Gateway gateway = new Gateway();
        gateway.setIdGateway(dto.getIdGateway());
        gateway.setTipo(dto.getTipo());
        // proceso se setea en el service
        return gateway;
    }
}