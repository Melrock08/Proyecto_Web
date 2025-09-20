package com.melrock.proyecto_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melrock.proyecto_web.model.Proceso;

@Repository
public interface ProcesoRepository extends JpaRepository<Proceso, Long> {
    // Ejemplo: listar procesos por empresa
    java.util.List<Proceso> findByEmpresaIdEmpresa(Long idEmpresa);
}