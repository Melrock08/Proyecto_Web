package com.melrock.proyecto_web.dto;

import lombok.Data;

@Data
public class UsuarioRegistroDTO {

    private Long idUsuario;

    private String nombre;

    private String correo;

    private String contrasena;

    private String rolSistema; // ADMIN 

    private String nombreEmpresa ;

    private String nit;

    private String correoContacto;

}
