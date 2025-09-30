package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.dto.EmpresaDTO;
import com.melrock.proyecto_web.model.Empresa;
import com.melrock.proyecto_web.model.Usuario;
import com.melrock.proyecto_web.repository.EmpresaRepository;
import com.melrock.proyecto_web.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final ModelMapper modelMapper;
    
    // Crear empresa
    public EmpresaDTO crearEmpresa(EmpresaDTO dto) {
        Empresa empresa = modelMapper.map(dto, Empresa.class);
        Empresa nuevaEmpresa = empresaRepository.save(empresa);
        return convertirADTO(nuevaEmpresa);
    }  
    
    // Listar todas las empresas
    public List<EmpresaDTO> listarEmpresas() {
        return empresaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar por id
    public Optional<EmpresaDTO> buscarPorId(Long id) {
        return empresaRepository.findById(id).map(this::convertirADTO);
    }

    // Buscar por NIT
    public EmpresaDTO buscarPorNit(String nit) {
        Empresa empresa = empresaRepository.findByNit(nit);
        return convertirADTO(empresa);
    }

    // Eliminar empresa
    public void eliminarEmpresa(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa no encontrada con id: " + id);
        }
        empresaRepository.deleteById(id);
    }

    // Actualizar empresa
    public EmpresaDTO actualizarEmpresa(Long id, EmpresaDTO dto) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + id));

        empresa.setNombre(dto.getNombre());
        empresa.setNit(dto.getNit());
        empresa.setCorreoContacto(dto.getCorreoContacto());

        Empresa actualizado = empresaRepository.save(empresa);
        return convertirADTO(actualizado);
    }

    // ===== Métodos auxiliares =====
    private EmpresaDTO convertirADTO(Empresa empresa) {
        return modelMapper.map(empresa, EmpresaDTO.class);
    }
}
