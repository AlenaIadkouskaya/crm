package com.iadkouskaya.crm.service.company;

import com.iadkouskaya.crm.model.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface CompanyService {
    Page<Company> findAll(int page, int size);
    void save(Company company, MultipartFile logoFile) throws IOException;
    String saveLogo(MultipartFile logoFile) throws IOException;
    void deleteById(Long id);
    Company findById(Long id);
    long count();
    int getLastPage(int pageSize);
    boolean isValid(Company company);
}
