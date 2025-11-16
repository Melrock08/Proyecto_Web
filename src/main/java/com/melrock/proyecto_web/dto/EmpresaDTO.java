package com.melrock.proyecto_web.dto;

import lombok.Data;

@Data
public class EmpresaDTO {

    private Long idEmpresa;

    private String nombreEmpresa;

    private String nit;

    private String correoContacto;
}
