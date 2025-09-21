package com.iadkouskaya.crm.mapper;

import com.iadkouskaya.crm.model.entity.dto.EmployeeDTO;
import com.iadkouskaya.crm.model.entity.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee toEntity(EmployeeDTO dto) {
        if (dto == null) return null;
        Employee employee = new Employee();
        employee.setFirstName(dto.firstName());
        employee.setLastName(dto.lastName());
        employee.setEmail(dto.email());
        employee.setPhone(dto.phone());
        return employee;
    }

    public EmployeeDTO toDTO(Employee employee) {
        if (employee == null) return null;
        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone()
        );
    }
}
