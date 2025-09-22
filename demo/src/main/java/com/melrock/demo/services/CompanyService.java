package com.melrock.demo.services;

import com.melrock.demo.dto.CompanyDTO;
import com.melrock.demo.models.Company;
import com.melrock.demo.repositories.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyDTO> findAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<CompanyDTO> findCompanyById(Long id) {
        return companyRepository.findById(id).map(this::convertToDto);
    }

    @Transactional
    public CompanyDTO createCompany(CompanyDTO companyDto) {
        if (companyRepository.findByNit(companyDto.getNit()).isPresent()) {
            throw new RuntimeException("Company with this NIT already exists.");
        }
        Company company = convertToEntity(companyDto);
        Company savedCompany = companyRepository.save(company);
        return convertToDto(savedCompany);
    }

    @Transactional
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDto) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        existingCompany.setName(companyDto.getName());
        existingCompany.setContactEmail(companyDto.getContactEmail());

        Company updatedCompany = companyRepository.save(existingCompany);
        return convertToDto(updatedCompany);
    }

    @Transactional
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    private CompanyDTO convertToDto(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setNit(company.getNit());
        dto.setName(company.getName());
        dto.setContactEmail(company.getContactEmail());
        return dto;
    }

    private Company convertToEntity(CompanyDTO dto) {
        Company company = new Company();
        company.setId(dto.getId());
        company.setNit(dto.getNit());
        company.setName(dto.getName());
        company.setContactEmail(dto.getContactEmail());
        return company;
    }
}
