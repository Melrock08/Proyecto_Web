package com.melrock.demo.services;

import com.melrock.demo.models.Gateaway;
import com.melrock.demo.models.Process;
import com.melrock.demo.repositories.ProcessRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.melrock.demo.repositories.GateawayRepository;
import com.melrock.demo.dto.GateawayDTO; // Corrected import
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GateawayService {
    private final GateawayRepository gateawayRepository;
    private final ProcessRepository processRepository;

    @Autowired
    public GateawayService(GateawayRepository gateawayRepository, ProcessRepository processRepository) {
        this.gateawayRepository = gateawayRepository;
        this.processRepository = processRepository;
    }

    public List<GateawayDTO> findAllGateaways() {
        return gateawayRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<GateawayDTO> findGateawayById(Long id) {
        return gateawayRepository.findById(id).map(this::convertToDto);
    }

    @Transactional
    public GateawayDTO createGateaway(Long processId, GateawayDTO gateawayDto) { // Correct parameter name
        Process process = processRepository.findById(processId)
                .orElseThrow(() -> new RuntimeException("Process not found"));

        Gateaway gateaway = convertToEntity(gateawayDto); // Correct variable name
        gateaway.setProcess(process);
        Gateaway savedGateaway = gateawayRepository.save(gateaway);
        return convertToDto(savedGateaway);
    }

    @Transactional
    public GateawayDTO updateGateaway(Long id, GateawayDTO gateawayDto) {
        Gateaway existingGateaway = gateawayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gateway not found"));

        existingGateaway.setName(gateawayDto.getName());
        existingGateaway.setType(gateawayDto.getType());
        existingGateaway.setConditions(gateawayDto.getConditions());

        Gateaway updatedGateaway = gateawayRepository.save(existingGateaway);
        return convertToDto(updatedGateaway);
    }

    @Transactional
    public void deleteGateaway(Long id) {
        gateawayRepository.deleteById(id);
    }

    private GateawayDTO convertToDto(Gateaway gateaway) {
        GateawayDTO dto = new GateawayDTO(); // Corrected class name
        dto.setId(gateaway.getId());
        dto.setName(gateaway.getName());
        dto.setType(gateaway.getType());
        dto.setConditions(gateaway.getConditions());
        return dto;
    }

    private Gateaway convertToEntity(GateawayDTO dto) {
        Gateaway gateaway = new Gateaway();
        gateaway.setId(dto.getId());
        gateaway.setName(dto.getName());
        gateaway.setType(dto.getType());
        gateaway.setConditions(dto.getConditions());
        return gateaway;
    }
}