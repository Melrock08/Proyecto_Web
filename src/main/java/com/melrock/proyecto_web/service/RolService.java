package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.dto.RolDTO;
import com.melrock.proyecto_web.model.Rol;
import com.melrock.proyecto_web.model.Empresa;
import com.melrock.proyecto_web.repository.RolRepository;
import com.melrock.proyecto_web.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;
    private final EmpresaRepository empresaRepository;
    private final ModelMapper modelMapper;
    
    // Crear rol
    public RolDTO crearRol(RolDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new RuntimeException("El rol debe tener un nombre");
        }

        Rol rol = modelMapper.map(dto, Rol.class);

        if (dto.getIdEmpresa() != null) {
            Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            rol.setEmpresa(empresa);
        }

        Rol guardado = rolRepository.save(rol);
        return convertirADTO(guardado);
    }

    // Editar rol
    public RolDTO editarRol(Long id, RolDTO dto) {
        Rol existente = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());

        if (dto.getIdEmpresa() != null) {
            Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            existente.setEmpresa(empresa);
        }

        Rol actualizado = rolRepository.save(existente);
        return convertirADTO(actualizado);
    }

    // Listar todos los roles
    public List<RolDTO> listarRoles() {
        return rolRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar por id
    public Optional<RolDTO> buscarPorId(Long id) {
        return rolRepository.findById(id).map(this::convertirADTO);
    }

    // Eliminar rol
    public void eliminarRol(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        if (rol.getEmpresa() != null && rol.getEmpresa().getProcesos() != null) {
            boolean enUso = rol.getEmpresa().getProcesos().stream()
                    .flatMap(proceso -> proceso.getActividades().stream())
                    .anyMatch(actividad -> actividad.getRol().getIdRol().equals(rol.getIdRol()));

            if (enUso) {
                throw new RuntimeException("No se puede eliminar el rol, está asignado a actividades");
            }
        }

        rolRepository.deleteById(id);
    }

    // ===== Método auxiliar =====
    private RolDTO convertirADTO(Rol rol) {
        RolDTO dto = modelMapper.map(rol, RolDTO.class);
        if (rol.getEmpresa() != null) {
            dto.setIdEmpresa(rol.getEmpresa().getIdEmpresa());
        }
        return dto;
    }
}
