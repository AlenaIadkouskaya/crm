package com.iadkouskaya.crm.service.employee;

import com.iadkouskaya.crm.model.entity.dto.EmployeeDTO;
import com.iadkouskaya.crm.model.entity.entity.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    Page<Employee> findByCompanyId(Long companyId, int page, int size);
    void save(Employee employee);
    void deleteById(Long employeeId);
    Employee findById(Long id);
    Employee findEmployeeInCompany(Long companyId, Long employeeId);
    long countByCompanyId(Long companyId);
    void saveEmployee(EmployeeDTO employeeDTO, Long companyId);
    void updateEmployee(Long employeeId, EmployeeDTO employeeDTO, Long companyId);
    EmployeeDTO getEmployeeDTOById(Long employeeId);
}
