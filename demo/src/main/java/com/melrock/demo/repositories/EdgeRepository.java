package com.melrock.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melrock.demo.models.Edge;

@Repository
public interface EdgeRepository extends JpaRepository<Edge, Long> {
}
