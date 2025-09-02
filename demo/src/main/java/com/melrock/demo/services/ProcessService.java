package com.melrock.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melrock.demo.repositories.ProcessRepository;

import jakarta.transaction.Transactional;

@Service
public class ProcessService {

  @Autowired
  private ProcessRepository repo;
  public ProcessService(ProcessRepository repo) { this.repo = repo; }

  @Transactional
  public Process create(Process p) { return repo.save(p); }
    
  
}
