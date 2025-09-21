package com.iadkouskaya.crm.model.entity.dto;

import org.springframework.web.multipart.MultipartFile;

public record CompanyDTO(
        Long id,
        String name,
        String email,
        String logo,
        String website,
        MultipartFile logoFile
) {}
