package com.melrock.demo.repositories;

import com.melrock.demo.models.Gateaway;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface GateawayRepository extends JpaRepository<Gateaway, Long> {
}
