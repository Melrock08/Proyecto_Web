package com.melrock.proyecto_web.mapper;

import com.melrock.proyecto_web.dto.ProcesoDTO;
import com.melrock.proyecto_web.model.Proceso;

public class ProcesoMapper {

    public static ProcesoDTO toDTO(Proceso proceso) {
        ProcesoDTO dto = new ProcesoDTO();
        dto.setIdProceso(proceso.getIdProceso());
        dto.setNombre(proceso.getNombre());
        dto.setDescripcion(proceso.getDescripcion());
        dto.setCategoria(proceso.getCategoria());
        dto.setEstado(proceso.getEstado());
        dto.setIdEmpresa(proceso.getEmpresa() != null ? proceso.getEmpresa().getIdEmpresa() : null);
        return dto;
    }

    public static Proceso toEntity(ProcesoDTO dto) {
        Proceso proceso = new Proceso();
        proceso.setIdProceso(dto.getIdProceso());
        proceso.setNombre(dto.getNombre());
        proceso.setDescripcion(dto.getDescripcion());
        proceso.setCategoria(dto.getCategoria());
        proceso.setEstado(dto.getEstado());
        // La empresa se setea en el Service
        return proceso;
    }
}
