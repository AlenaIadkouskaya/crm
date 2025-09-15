package com.iadkouskaya.crm.controller;

import com.iadkouskaya.crm.model.entity.Company;
import com.iadkouskaya.crm.model.entity.Employee;
import com.iadkouskaya.crm.service.company.CompanyService;
import com.iadkouskaya.crm.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    @Value("${app.pagination.page-size}")
    private int pageSize;

    public EmployeeController(EmployeeService employeeService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @GetMapping("/companies/{companyId}/employees")
    public String getEmployeesByCompany(@PathVariable Long companyId,
                                        @RequestParam(defaultValue = "0") int page,
                                        Model model) {
        Page<Employee> employeesPage = employeeService.findByCompanyId(companyId, page, pageSize);
        Company company = companyService.findById(companyId);

        model.addAttribute("employeesPage", employeesPage);
        model.addAttribute("company", company);
        return "employees/list";
    }

    @GetMapping("/companies/{companyId}/employees/new")
    public String showAddEmployeeForm(@PathVariable Long companyId, Model model) {
        Employee employee = new Employee();
        Company company = companyService.findById(companyId);

        employee.setCompany(company);
        model.addAttribute("employee", employee);
        model.addAttribute("company", company);

        return "employees/form";
    }

    @PostMapping("/companies/{companyId}/employees")
    public String saveEmployee(@PathVariable Long companyId,
                               @ModelAttribute Employee employee) {
        Company company = companyService.findById(companyId);

        employee.setCompany(company);
        employeeService.save(employee);

        return "redirect:/companies/" + companyId + "/employees";
    }

    @PostMapping("/companies/{companyId}/employees/{employeeId}/delete")
    public String deleteEmployee(@PathVariable Long companyId,
                                 @PathVariable Long employeeId) {
        employeeService.deleteById(employeeId);
        return "redirect:/companies/" + companyId + "/employees";
    }

    @GetMapping("/companies/{companyId}/employees/{employeeId}/edit")
    public String showEditEmployeeForm(@PathVariable Long companyId,
                                       @PathVariable Long employeeId,
                                       Model model) {
        Employee employee = employeeService.findEmployeeInCompany(companyId, employeeId);
        Company company = employee.getCompany();

        model.addAttribute("employee", employee);
        model.addAttribute("company", company);

        return "employees/form";
    }

    @PostMapping("/companies/{companyId}/employees/{employeeId}")
    public String updateEmployee(@PathVariable Long companyId,
                                 @PathVariable Long employeeId,
                                 @ModelAttribute Employee updatedEmployee) {
        Employee existingEmployee = employeeService.findById(employeeId);

        Company company = companyService.findById(companyId);

        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setPhone(updatedEmployee.getPhone());
        existingEmployee.setCompany(company);

        employeeService.save(existingEmployee);

        return "redirect:/companies/" + companyId + "/employees";
    }
}
