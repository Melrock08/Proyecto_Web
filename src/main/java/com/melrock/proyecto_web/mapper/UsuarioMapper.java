package com.melrock.proyecto_web.mapper;

import com.melrock.proyecto_web.dto.UsuarioDTO;
import com.melrock.proyecto_web.model.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setIdEmpresa(usuario.getEmpresa() != null ? usuario.getEmpresa().getIdEmpresa() : null);
        return dto;
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.getIdUsuario());
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        // La empresa se setea aparte desde el service
        return usuario;
    }
}

