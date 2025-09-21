package com.iadkouskaya.crm.service.employee;

import com.iadkouskaya.crm.mapper.EmployeeMapper;
import com.iadkouskaya.crm.model.entity.dto.EmployeeDTO;
import com.iadkouskaya.crm.model.entity.entity.Company;
import com.iadkouskaya.crm.model.entity.entity.Employee;
import com.iadkouskaya.crm.repository.EmployeeRepository;
import com.iadkouskaya.crm.service.company.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final CompanyService companyService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, @Lazy CompanyService companyService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.companyService = companyService;
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

    @Override
    public void saveEmployee(EmployeeDTO dto, Long companyId) {
        Company company = companyService.findById(companyId);
        Employee employee = employeeMapper.toEntity(dto);
        employee.setCompany(company);
        employeeRepository.save(employee);
    }

    @Override
    public void updateEmployee(Long employeeId, EmployeeDTO dto, Long companyId) {
        Employee existing = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Company company = companyService.findById(companyId);

        existing.setFirstName(dto.firstName());
        existing.setLastName(dto.lastName());
        existing.setEmail(dto.email());
        existing.setPhone(dto.phone());
        existing.setCompany(company);

        employeeRepository.save(existing);
    }

    @Override
    public EmployeeDTO getEmployeeDTOById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return employeeMapper.toDTO(employee);
    }
}
