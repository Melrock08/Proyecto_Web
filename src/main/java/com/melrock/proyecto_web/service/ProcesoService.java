package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.dto.EmpresaDTO;
import com.melrock.proyecto_web.dto.ProcesoDTO;
import com.melrock.proyecto_web.model.Empresa;
import com.melrock.proyecto_web.model.Proceso;
import com.melrock.proyecto_web.repository.ProcesoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcesoService {

    private final ProcesoRepository procesoRepository;
    private final EmpresaService empresaService;
    private final ModelMapper modelMapper;

    // Crear proceso
    public ProcesoDTO crearProceso(ProcesoDTO dto) {
        Proceso proceso = modelMapper.map(dto, Proceso.class);
        proceso.setEstado("BORRADOR"); // valor por defecto

        // Vincular con la empresa via EmpresaService
        EmpresaDTO empresaDto = empresaService.buscarPorId(dto.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        Empresa empresa = modelMapper.map(empresaDto, Empresa.class);
        proceso.setEmpresa(empresa);

        Proceso guardado = procesoRepository.save(proceso);
        return convertirADTO(guardado);
    }

    // Editar proceso
    public ProcesoDTO editarProceso(Long id, ProcesoDTO dto) {
        Proceso existente = procesoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proceso no encontrado"));

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setCategoria(dto.getCategoria());
        existente.setEstado(dto.getEstado());

        // Si se envía un nuevo idEmpresa, actualizarlo vía service
        if (dto.getIdEmpresa() != null) {
            EmpresaDTO empresaDto = empresaService.buscarPorId(dto.getIdEmpresa())
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            Empresa empresa = modelMapper.map(empresaDto, Empresa.class);
            existente.setEmpresa(empresa);
        }

        Proceso actualizado = procesoRepository.save(existente);
        return convertirADTO(actualizado);
    }

    // Listar todos
    public List<ProcesoDTO> listarProcesos() {
        return procesoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar por id
    public Optional<ProcesoDTO> buscarPorId(Long id) {
        return procesoRepository.findById(id).map(this::convertirADTO);
    }

    // Listar por empresa
    public List<ProcesoDTO> listarPorEmpresa(Long idEmpresa) {
        return procesoRepository.findByEmpresaIdEmpresa(idEmpresa)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Desactivar
    public ProcesoDTO desactivarProceso(Long id) {
        Proceso existente = procesoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proceso no encontrado"));

        existente.setEstado("INACTIVO");
        Proceso actualizado = procesoRepository.save(existente);

        return convertirADTO(actualizado);
    }

    // ===== Métodos auxiliares =====
    private ProcesoDTO convertirADTO(Proceso proceso) {
        ProcesoDTO dto = modelMapper.map(proceso, ProcesoDTO.class);
        if (proceso.getEmpresa() != null) {
            dto.setIdEmpresa(proceso.getEmpresa().getIdEmpresa());
        }
        return dto;
    }
}
