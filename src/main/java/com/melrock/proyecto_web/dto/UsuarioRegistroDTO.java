    package com.melrock.proyecto_web.dto;

    import lombok.Data;

    @Data
    public class UsuarioRegistroDTO {

        private String nombre;

        private String correo;

        private String contrasena;

        private String rolSistema; 

        private EmpresaRegistroDTO empresa;

    }
    