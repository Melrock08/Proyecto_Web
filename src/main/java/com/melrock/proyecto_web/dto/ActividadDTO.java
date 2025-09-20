package com.melrock.proyecto_web.dto;

import lombok.Data;

@Data
public class ActividadDTO {
    private Long idActividad;
    private String nombre;
    private String descripcion;
    private Long idRol;
    private Long idProceso;
}
