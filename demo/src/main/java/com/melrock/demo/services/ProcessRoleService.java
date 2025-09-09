package com.melrock.demo.services;

import com.melrock.demo.dto.ProcessRoleDTO;
import com.melrock.demo.models.ProcessRole;
import com.melrock.demo.models.Company;
import com.melrock.demo.repositories.ProcessRoleRepository;
import com.melrock.demo.repositories.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProcessRoleService {

    private final ProcessRoleRepository processRoleRepository;
    private final CompanyRepository companyRepository; // Necesitas un repositorio para Company
    // También necesitarás un ActivityRepository para la validación de borrado

    @Autowired
    public ProcessRoleService(ProcessRoleRepository processRoleRepository, CompanyRepository companyRepository) {
        this.processRoleRepository = processRoleRepository;
        this.companyRepository = companyRepository;
    }

    public List<ProcessRoleDTO> findAllRoles() {
        return processRoleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<ProcessRoleDTO> findRoleById(Long id) {
        return processRoleRepository.findById(id).map(this::convertToDto);
    }

    @Transactional
    public ProcessRoleDTO createRole(Long companyId, ProcessRoleDTO roleDto) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        ProcessRole role = convertToEntity(roleDto);
        role.setCompany(company);
        ProcessRole savedRole = processRoleRepository.save(role);
        return convertToDto(savedRole);
    }

    @Transactional
    public ProcessRoleDTO updateRole(Long id, ProcessRoleDTO roleDto) {
        ProcessRole existingRole = processRoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        existingRole.setName(roleDto.getName());
        existingRole.setDescription(roleDto.getDescription());

        ProcessRole updatedRole = processRoleRepository.save(existingRole);
        return convertToDto(updatedRole);
    }

    @Transactional
    public void deleteRole(Long id) {
        // Implementación de la validación: Se valida que el rol no esté asignado a ninguna actividad antes de eliminarlo[cite: 113, 177, 192].
        // Necesitas inyectar un repositorio de actividades y realizar una consulta para verificar si el rol está en uso.
        // Ejemplo: if (activityRepository.existsByProcessRoleId(id)) {
        //              throw new RuntimeException("Cannot delete role. It is assigned to an activity.");
        //          }

        processRoleRepository.deleteById(id);
    }

    private ProcessRoleDTO convertToDto(ProcessRole role) {
        ProcessRoleDTO dto = new ProcessRoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        return dto;
    }

    private ProcessRole convertToEntity(ProcessRoleDTO dto) {
        ProcessRole role = new ProcessRole();
        role.setId(dto.getId());
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        return role;
    }
}