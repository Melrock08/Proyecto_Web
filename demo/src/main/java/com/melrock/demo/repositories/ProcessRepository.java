package com.melrock.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melrock.demo.models.Process;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {

    Object findWithGraphById(Long id);
    // Add custom query methods if needed

    java.lang.Process save(java.lang.Process p);
}