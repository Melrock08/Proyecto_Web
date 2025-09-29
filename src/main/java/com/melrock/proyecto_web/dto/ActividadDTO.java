package com.melrock.proyecto_web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ActividadDTO {

    private Long idActividad;

    @NotBlank(message = "El nombre de la actividad es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de la actividad debe tener entre 3 y 50 caracteres")
    private String nombre;

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    private String descripcion;

    @NotBlank(message = "El tipo de actividad es obligatorio")
    @Size(max = 30, message = "El tipo no puede superar los 30 caracteres")
    private String tipo;

    @NotNull(message = "El rol asociado es obligatorio")
    private Long idRol;

    @NotNull(message = "El proceso asociado es obligatorio")
    private Long idProceso;
}

