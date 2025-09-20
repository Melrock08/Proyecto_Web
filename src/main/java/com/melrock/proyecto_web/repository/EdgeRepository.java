package com.melrock.proyecto_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melrock.proyecto_web.model.Edge;

@Repository
public interface EdgeRepository extends JpaRepository<Edge, Long> {
}