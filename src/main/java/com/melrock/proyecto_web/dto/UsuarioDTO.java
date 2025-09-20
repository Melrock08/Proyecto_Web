package com.melrock.proyecto_web.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long idUsuario;
    private String nombre;
    private String correo;
    private String rol; // ADMIN, EDITOR, LECTOR
    private Long idEmpresa;
}