package com.iadkouskaya.crm.service.employee;

import com.iadkouskaya.crm.model.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface EmployeeService {
    Page<Employee> findByCompanyId(Long companyId, int page, int size);

    void save(Employee employee);

    void deleteById(Long employeeId);

    Optional<Employee> findById(Long id);
}
