package com.melrock.proyecto_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melrock.proyecto_web.model.Actividad;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
}
