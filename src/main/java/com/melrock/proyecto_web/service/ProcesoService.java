package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.repository.ProcesoRepository;
import org.springframework.stereotype.Service;
import com.melrock.proyecto_web.model.Proceso;

import java.util.List;
import java.util.Optional;

@Service
public class ProcesoService {

    private final ProcesoRepository procesoRepository;

    public ProcesoService(ProcesoRepository procesoRepository) {
        this.procesoRepository = procesoRepository;
    }

    // Crear o actualizar proceso
     public Proceso crearProceso(Proceso proceso) {
        proceso.setEstado("BORRADOR"); // por defecto
        return procesoRepository.save(proceso);
    }

    // Editar proceso
    public Proceso editarProceso(Long id, Proceso datosActualizados) {
        Optional<Proceso> existenteOpt = procesoRepository.findById(id);

        if (existenteOpt.isEmpty()) {
            throw new RuntimeException("Proceso no encontrado");
        }

        Proceso existente = existenteOpt.get();
        existente.setNombre(datosActualizados.getNombre());
        existente.setDescripcion(datosActualizados.getDescripcion());
        existente.setCategoria(datosActualizados.getCategoria());
        existente.setEstado(datosActualizados.getEstado());

        return procesoRepository.save(existente);
    }

    // Listar todos los procesos
    public List<Proceso> listarProcesos() {
        return procesoRepository.findAll();
    }

    // Buscar proceso por id
    public Optional<Proceso> buscarPorId(Long id) {
        return procesoRepository.findById(id);
    }

    // Listar procesos por empresa
    public List<Proceso> listarPorEmpresa(Long idEmpresa) {
        return procesoRepository.findByEmpresaIdEmpresa(idEmpresa);
    }

    // Eliminar proceso (marcar como inactivo en lugar de borrar)
    public Proceso desactivarProceso(Long id) {
        Optional<Proceso> existenteOpt = procesoRepository.findById(id);

        if (existenteOpt.isEmpty()) {
            throw new RuntimeException("Proceso no encontrado");
        }

        Proceso existente = existenteOpt.get();
        existente.setEstado("INACTIVO");

        return procesoRepository.save(existente);
    }
}