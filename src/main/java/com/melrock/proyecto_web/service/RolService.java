package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.repository.RolRepository;
import org.springframework.stereotype.Service;
import com.melrock.proyecto_web.model.Rol;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // Crear rol
    public Rol crearRol(Rol rol) {
        if (rol.getNombre() == null || rol.getNombre().isBlank()) {
            throw new RuntimeException("El rol debe tener un nombre");
        }
        return rolRepository.save(rol);
    }

    // Editar rol
    public Rol editarRol(Long id, Rol datosActualizados) {
        Optional<Rol> existenteOpt = rolRepository.findById(id);

        if (existenteOpt.isEmpty()) {
            throw new RuntimeException("Rol no encontrado");
        }

        Rol existente = existenteOpt.get();
        existente.setNombre(datosActualizados.getNombre());
        existente.setDescripcion(datosActualizados.getDescripcion());

        return rolRepository.save(existente);
    }

    // Listar todos los roles
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    // Buscar rol por id
    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id);
    }

    // Eliminar rol
      public void eliminarRol(Long id) {
        Optional<Rol> existenteOpt = rolRepository.findById(id);

        if (existenteOpt.isEmpty()) {
            throw new RuntimeException("Rol no encontrado");
        }

        Rol rol = existenteOpt.get();

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
}