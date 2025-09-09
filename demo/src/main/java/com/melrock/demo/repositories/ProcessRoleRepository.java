package com.melrock.demo.repositories;

import com.melrock.demo.models.ProcessRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessRoleRepository extends JpaRepository<ProcessRole, Long> {
}