package com.melrock.proyecto_web.dto;

import lombok.Data;

@Data
public class EdgeDTO {
    private Long idEdge;
    private Long idProceso;
    private Long idOrigenActividad;
    private Long idOrigenGateway;
    private Long idDestinoActividad;
    private Long idDestinoGateway;
}
