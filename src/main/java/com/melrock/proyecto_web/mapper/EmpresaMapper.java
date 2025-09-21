package com.melrock.proyecto_web.mapper;

import com.melrock.proyecto_web.dto.EmpresaDTO;
import com.melrock.proyecto_web.model.Empresa;

public class EmpresaMapper {

    public static EmpresaDTO toDTO(Empresa empresa) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setIdEmpresa(empresa.getIdEmpresa());
        dto.setNombre(empresa.getNombre());
        dto.setNit(empresa.getNit());
        return dto;
    }

    public static Empresa toEntity(EmpresaDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(dto.getIdEmpresa());
        empresa.setNombre(dto.getNombre());
        empresa.setNit(dto.getNit());
        return empresa;
    }
}