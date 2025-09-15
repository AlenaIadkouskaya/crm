package com.iadkouskaya.crm.repository;

import com.iadkouskaya.crm.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Page<Employee> findByCompanyId(Long companyId, Pageable pageable);
    long countByCompanyId(Long companyId);
}
