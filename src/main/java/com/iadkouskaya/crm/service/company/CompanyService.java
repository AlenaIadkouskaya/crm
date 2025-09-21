package com.iadkouskaya.crm.service.company;

import com.iadkouskaya.crm.model.entity.dto.CompanyDTO;
import com.iadkouskaya.crm.model.entity.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CompanyService {
    Page<Company> findAll(int page, int size);
    void save(CompanyDTO company, MultipartFile logoFile) throws IOException;
    String saveLogo(MultipartFile logoFile) throws IOException;
    void deleteById(Long id);
    Company findById(Long id);
    long count();
    int getLastPage(int pageSize);
    boolean isValid(CompanyDTO company);
    CompanyDTO getCompanyDTOById(Long id);
    void update(Long id, CompanyDTO companyDTO, MultipartFile logoFile) throws IOException;
}
