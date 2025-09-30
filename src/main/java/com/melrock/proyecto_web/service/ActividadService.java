package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.dto.ActividadDTO;
import com.melrock.proyecto_web.model.Actividad;
import com.melrock.proyecto_web.model.Rol;
import com.melrock.proyecto_web.repository.ActividadRepository;
import com.melrock.proyecto_web.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActividadService {

    private final ActividadRepository actividadRepository;
    private final RolRepository rolRepository;
    private final ModelMapper modelMapper;

    // Crear actividad
    public ActividadDTO crearActividad(ActividadDTO dto) {
        Actividad actividad = modelMapper.map(dto, Actividad.class);

        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        actividad.setRol(rol);

        Actividad guardada = actividadRepository.save(actividad);
        return convertirADTO(guardada);
    }

    // Editar actividad
    public ActividadDTO editarActividad(Long id, ActividadDTO dto) {
        Actividad existente = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        existente.setNombre(dto.getNombre());
        existente.setTipo(dto.getTipo());
        existente.setDescripcion(dto.getDescripcion());

        if (dto.getIdRol() != null) {
            Rol rol = rolRepository.findById(dto.getIdRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            existente.setRol(rol);
        }

        Actividad actualizado = actividadRepository.save(existente);
        return convertirADTO(actualizado);
    }

    // Listar todas
    public List<ActividadDTO> listarActividades() {
        return actividadRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar por id
    public Optional<ActividadDTO> buscarPorId(Long id) {
        return actividadRepository.findById(id).map(this::convertirADTO);
    }

    // Eliminar actividad
    public void eliminarActividad(Long id) {
        if (!actividadRepository.existsById(id)) {
            throw new RuntimeException("Actividad no encontrada");
        }
        actividadRepository.deleteById(id);
    }

    // ===== Métodos auxiliares =====
    private ActividadDTO convertirADTO(Actividad actividad) {
        ActividadDTO dto = modelMapper.map(actividad, ActividadDTO.class);
        dto.setIdRol(actividad.getRol().getIdRol());
        return dto;
    }
}
