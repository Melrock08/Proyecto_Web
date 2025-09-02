package com.melrock.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melrock.demo.models.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

}

