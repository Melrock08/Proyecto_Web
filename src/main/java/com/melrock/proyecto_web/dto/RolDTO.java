package com.melrock.proyecto_web.dto;

import lombok.Data;

@Data
public class RolDTO {
    
    private Long idRol;

    private String nombre;

    private String descripcion;

    private Long idEmpresa;
}
