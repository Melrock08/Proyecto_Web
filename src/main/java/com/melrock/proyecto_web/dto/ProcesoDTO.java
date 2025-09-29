package com.melrock.proyecto_web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProcesoDTO {

    private Long idProceso;

    @NotBlank(message = "El nombre del proceso es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    private String descripcion;

    @NotBlank(message = "La categoría del proceso es obligatoria")
    @Size(max = 30, message = "La categoría no puede superar los 30 caracteres")
    private String categoria;

    @NotBlank(message = "El estado del proceso es obligatorio (Borrador, Activo, Inactivo)")
    private String estado;

    @NotNull(message = "La empresa es obligatoria")
    private Long idEmpresa;
}
