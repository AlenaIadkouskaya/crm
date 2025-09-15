package com.iadkouskaya.crm.service.employee;

import com.iadkouskaya.crm.model.entity.Employee;
import com.iadkouskaya.crm.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Page<Employee> findByCompanyId(Long companyId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findByCompanyId(companyId, pageable);
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Nie znaleziono pracownika do usunięcia o ID: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono pracownika o ID: " + id));
    }

    @Override
    public Employee findEmployeeInCompany(Long companyId, Long employeeId) {
        Employee employee = findById(employeeId);
        if (!employee.getCompany().getId().equals(companyId)) {
            throw new EntityNotFoundException("Pracownik nie należy do tej firmy.");
        }
        return employee;
    }

    @Override
    public long countByCompanyId(Long companyId) {
        return employeeRepository.countByCompanyId(companyId);
    }
}
