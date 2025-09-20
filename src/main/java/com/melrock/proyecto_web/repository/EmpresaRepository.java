package com.melrock.proyecto_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melrock.proyecto_web.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    // Ejemplo: buscar empresa por NIT
    Empresa findByNit(String nit);
}
