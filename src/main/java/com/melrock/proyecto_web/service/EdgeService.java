package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.repository.EdgeRepository;
import org.springframework.stereotype.Service;
import com.melrock.proyecto_web.model.Edge;

import java.util.List;
import java.util.Optional;

@Service
public class EdgeService {

    private final EdgeRepository edgeRepository;

    public EdgeService(EdgeRepository edgeRepository) {
        this.edgeRepository = edgeRepository;
    }

    // Crear o actualizar arco
    public Edge crearEdge(Edge edge) {
        if (edge.getOrigenActividad() == null && edge.getOrigenGateway() == null) {
            throw new RuntimeException("El arco debe tener un origen (actividad o gateway)");
        }
        if (edge.getDestinoActividad() == null && edge.getDestinoGateway() == null) {
            throw new RuntimeException("El arco debe tener un destino (actividad o gateway)");
        }

        // Validar que todo pertenezca al mismo proceso
        Long idProceso = edge.getProceso().getIdProceso();

        if (edge.getOrigenActividad() != null && !edge.getOrigenActividad().getProceso().getIdProceso().equals(idProceso)) {
            throw new RuntimeException("El origen no pertenece al proceso del arco");
        }
        if (edge.getOrigenGateway() != null && !edge.getOrigenGateway().getProceso().getIdProceso().equals(idProceso)) {
            throw new RuntimeException("El origen no pertenece al proceso del arco");
        }
        if (edge.getDestinoActividad() != null && !edge.getDestinoActividad().getProceso().getIdProceso().equals(idProceso)) {
            throw new RuntimeException("El destino no pertenece al proceso del arco");
        }
        if (edge.getDestinoGateway() != null && !edge.getDestinoGateway().getProceso().getIdProceso().equals(idProceso)) {
            throw new RuntimeException("El destino no pertenece al proceso del arco");
        }

        return edgeRepository.save(edge);
    }

    // Editar arco (cambiar origen o destino)
    public Edge editarEdge(Long id, Edge datosActualizados) {
        Optional<Edge> existenteOpt = edgeRepository.findById(id);

        if (existenteOpt.isEmpty()) {
            throw new RuntimeException("Arco no encontrado");
        }

        Edge existente = existenteOpt.get();
        existente.setOrigenActividad(datosActualizados.getOrigenActividad());
        existente.setOrigenGateway(datosActualizados.getOrigenGateway());
        existente.setDestinoActividad(datosActualizados.getDestinoActividad());
        existente.setDestinoGateway(datosActualizados.getDestinoGateway());

        return edgeRepository.save(existente);
    }

    // Listar todos los arcos
    public List<Edge> listarEdges() {
        return edgeRepository.findAll();
    }

    // Buscar arco por id
    public Optional<Edge> buscarPorId(Long id) {
        return edgeRepository.findById(id);
    }

    // Eliminar arco
    public void eliminarEdge(Long id) {
        Optional<Edge> existenteOpt = edgeRepository.findById(id);

        if (existenteOpt.isEmpty()) {
            throw new RuntimeException("Arco no encontrado");
        }

        edgeRepository.deleteById(id);
    }
}
