package com.melrock.proyecto_web.mapper;

import com.melrock.proyecto_web.dto.RolDTO;
import com.melrock.proyecto_web.model.Rol;

public class RolMapper {

    public static RolDTO toDTO(Rol rol) {
        RolDTO dto = new RolDTO();
        dto.setIdRol(rol.getIdRol());
        dto.setNombre(rol.getNombre());
        dto.setDescripcion(rol.getDescripcion());
        dto.setIdEmpresa(rol.getEmpresa() != null ? rol.getEmpresa().getIdEmpresa() : null);
        return dto;
    }

    public static Rol toEntity(RolDTO dto) {
        Rol rol = new Rol();
        rol.setIdRol(dto.getIdRol());
        rol.setNombre(dto.getNombre());
        rol.setDescripcion(dto.getDescripcion());
        // empresa se setea en el service
        return rol;
    }
}