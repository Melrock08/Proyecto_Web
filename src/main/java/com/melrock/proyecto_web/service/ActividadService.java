package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.repository.ActividadRepository;
import org.springframework.stereotype.Service;
import com.melrock.proyecto_web.model.Actividad;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadService {

    private final ActividadRepository actividadRepository;

    public ActividadService(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    // Crear actividad
     public Actividad crearActividad(Actividad actividad) {
        if (actividad.getRol() == null) {
            throw new RuntimeException("La actividad debe tener un rol asignado");
        }
        return actividadRepository.save(actividad);
    }

    // Editar actividad
    public Actividad editarActividad(Long id, Actividad datosActualizados) {
        Optional<Actividad> existenteOpt = actividadRepository.findById(id);

        if (existenteOpt.isEmpty()) {
            throw new RuntimeException("Actividad no encontrada");
        }

        Actividad existente = existenteOpt.get();
        existente.setNombre(datosActualizados.getNombre());
        existente.setTipo(datosActualizados.getTipo());
        existente.setDescripcion(datosActualizados.getDescripcion());
        existente.setRol(datosActualizados.getRol());

        return actividadRepository.save(existente);
    }

    // Listar todas las actividades
    public List<Actividad> listarActividades() {
        return actividadRepository.findAll();
    }

    // Buscar actividad por id
    public Optional<Actividad> buscarPorId(Long id) {
        return actividadRepository.findById(id);
    }

    // Eliminar actividad
    public void eliminarActividad(Long id) {
        Optional<Actividad> existenteOpt = actividadRepository.findById(id);

        if (existenteOpt.isEmpty()) {
            throw new RuntimeException("Actividad no encontrada");
        }

        actividadRepository.deleteById(id);
    }
}
