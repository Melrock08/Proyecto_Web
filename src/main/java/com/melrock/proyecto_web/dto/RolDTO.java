package com.melrock.proyecto_web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RolDTO {
    
    private Long idRol;

    @NotBlank(message = "El nombre del rol no puede estar vacío")
    @Size(min = 3, max = 30, message = "El nombre del rol debe tener entre 3 y 30 caracteres")
    private String nombre;

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    private String descripcion;

    @NotNull(message = "La empresa es obligatoria")
    private Long idEmpresa;
}
