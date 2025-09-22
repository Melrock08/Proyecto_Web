package com.melrock.demo.repositories;

import com.melrock.demo.models.Gateway;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface GatewayRepository extends JpaRepository<Gateway, Long> {
    List<Gateway> findByProcessId(Long processId);
}
