package com.melrock.proyecto_web.dto;

import lombok.Data;

@Data
public class ProcesoDTO {
    private Long idProceso;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String estado; // Borrador, Activo, Inactivo
    private Long idEmpresa;
}