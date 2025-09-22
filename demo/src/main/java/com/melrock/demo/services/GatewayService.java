package com.melrock.demo.services;

import com.melrock.demo.models.Gateway;
import com.melrock.demo.models.Process;
import com.melrock.demo.repositories.ProcessRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.melrock.demo.repositories.GatewayRepository;
import com.melrock.demo.dto.GatewayDTO; // Corrected import
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GatewayService {
    private final GatewayRepository gateawayRepository;
    private final ProcessRepository processRepository;

    @Autowired
    public GatewayService(GatewayRepository gateawayRepository, ProcessRepository processRepository) {
        this.gateawayRepository = gateawayRepository;
        this.processRepository = processRepository;
    }

    public List<GatewayDTO> findAllGateaways() {
        return gateawayRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<GatewayDTO> findGateawayById(Long id) {
        return gateawayRepository.findById(id).map(this::convertToDto);
    }

    @Transactional
    public GatewayDTO createGateaway(Long processId, GatewayDTO gateawayDto) { // Correct parameter name
        Process process = processRepository.findById(processId)
                .orElseThrow(() -> new RuntimeException("Process not found"));

        Gateway gateaway = convertToEntity(gateawayDto); // Correct variable name
        gateaway.setProcess(process);
        Gateway savedGateaway = gateawayRepository.save(gateaway);
        return convertToDto(savedGateaway);
    }

    @Transactional
    public GatewayDTO updateGateaway(Long id, GatewayDTO gateawayDto) {
        Gateway existingGateaway = gateawayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gateway not found"));

        existingGateaway.setName(gateawayDto.getName());
        existingGateaway.setType(gateawayDto.getType());
        existingGateaway.setConditions(gateawayDto.getConditions());

        Gateway updatedGateaway = gateawayRepository.save(existingGateaway);
        return convertToDto(updatedGateaway);
    }

    @Transactional
    public void deleteGateaway(Long id) {
        gateawayRepository.deleteById(id);
    }

    private GatewayDTO convertToDto(Gateway gateaway) {
        GatewayDTO dto = new GatewayDTO(); // Corrected class name
        dto.setId(gateaway.getId());
        dto.setName(gateaway.getName());
        dto.setType(gateaway.getType());
        dto.setConditions(gateaway.getConditions());
        return dto;
    }

    private Gateway convertToEntity(GatewayDTO dto) {
        Gateway gateaway = new Gateway();
        gateaway.setId(dto.getId());
        gateaway.setName(dto.getName());
        gateaway.setType(dto.getType());
        gateaway.setConditions(dto.getConditions());
        return gateaway;
    }
}