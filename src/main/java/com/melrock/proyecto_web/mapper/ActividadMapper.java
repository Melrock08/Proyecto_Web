package com.melrock.proyecto_web.mapper;

import com.melrock.proyecto_web.dto.ActividadDTO;
import com.melrock.proyecto_web.model.Actividad;

public class ActividadMapper {

    public static ActividadDTO toDTO(Actividad actividad) {
        ActividadDTO dto = new ActividadDTO();
        dto.setIdActividad(actividad.getIdActividad());
        dto.setNombre(actividad.getNombre());
        dto.setDescripcion(actividad.getDescripcion());
        dto.setTipo(actividad.getTipo());
        dto.setIdRol(actividad.getRol() != null ? actividad.getRol().getIdRol() : null);
        dto.setIdProceso(actividad.getProceso() != null ? actividad.getProceso().getIdProceso() : null);
        return dto;
    }

    public static Actividad toEntity(ActividadDTO dto) {
        Actividad actividad = new Actividad();
        actividad.setIdActividad(dto.getIdActividad());
        actividad.setNombre(dto.getNombre());
        actividad.setDescripcion(dto.getDescripcion());
        actividad.setTipo(dto.getTipo());
        // Rol y Proceso se asignan en el service a partir de sus IDs
        return actividad;
    }
}

