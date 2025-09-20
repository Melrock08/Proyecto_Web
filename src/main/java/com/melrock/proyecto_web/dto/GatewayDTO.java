package com.melrock.proyecto_web.dto;

import lombok.Data;

@Data
public class GatewayDTO {
    private Long idGateway;
    private String tipo; // EXCLUSIVO, PARALELO, INCLUSIVO
    private Long idProceso;
}
