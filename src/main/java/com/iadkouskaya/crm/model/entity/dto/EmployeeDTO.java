package com.iadkouskaya.crm.model.entity.dto;

public record EmployeeDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone
){}