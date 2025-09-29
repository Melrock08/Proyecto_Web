package com.melrock.proyecto_web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class GatewayDTO {

    private Long idGateway;

    @NotBlank(message = "El tipo de gateway es obligatorio")
    @Pattern(regexp = "EXCLUSIVO|PARALELO|INCLUSIVO", 
             message = "El tipo de gateway debe ser EXCLUSIVO, PARALELO o INCLUSIVO")
    private String tipo;

    @NotNull(message = "El proceso es obligatorio")
    private Long idProceso;
}

