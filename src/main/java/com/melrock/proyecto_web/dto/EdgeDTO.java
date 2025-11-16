package com.melrock.proyecto_web.dto;

import lombok.Data;

@Data
public class EdgeDTO {

    private Long idEdge;

    private Long idProceso;


    // Al menos un origen (actividad o gateway) debería estar presente
    private Long idOrigenActividad;
    private Long idOrigenGateway;

    // Al menos un destino (actividad o gateway) debería estar presente
    private Long idDestinoActividad;
    private Long idDestinoGateway;
}

