package com.iadkouskaya.crm.mapper;

import com.iadkouskaya.crm.model.entity.dto.CompanyDTO;
import com.iadkouskaya.crm.model.entity.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public Company toEntity(CompanyDTO dto) {
        if (dto == null) return null;
        Company company = new Company();
        company.setName(dto.name());
        company.setEmail(dto.email());
        company.setLogo(dto.logo());
        company.setWebsite(dto.website());
        return company;
    }

    public CompanyDTO toDTO(Company company) {
        if (company == null) return null;
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getEmail(),
                company.getLogo(),
                company.getWebsite(),
                null
        );
    }
}
