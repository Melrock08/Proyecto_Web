package com.melrock.proyecto_web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EdgeDTO {

    private Long idEdge;

    @NotNull(message = "El proceso es obligatorio")
    private Long idProceso;

    // Al menos un origen (actividad o gateway) debería estar presente
    private Long idOrigenActividad;
    private Long idOrigenGateway;

    // Al menos un destino (actividad o gateway) debería estar presente
    private Long idDestinoActividad;
    private Long idDestinoGateway;
}

