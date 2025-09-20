package com.melrock.proyecto_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melrock.proyecto_web.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
}
