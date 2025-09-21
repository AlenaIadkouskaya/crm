package com.iadkouskaya.crm.controller;

import com.iadkouskaya.crm.mapper.EmployeeMapper;
import com.iadkouskaya.crm.model.entity.dto.EmployeeDTO;
import com.iadkouskaya.crm.model.entity.entity.Company;
import com.iadkouskaya.crm.model.entity.entity.Employee;
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
    private final EmployeeMapper employeeMapper;
    @Value("${app.pagination.page-size}")
    private int pageSize;

    public EmployeeController(EmployeeService employeeService, CompanyService companyService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.employeeMapper = employeeMapper;
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
        Company company = companyService.findById(companyId);
        model.addAttribute("employee", new EmployeeDTO(null, null, null, null, null));
        model.addAttribute("companyId", companyId);
        model.addAttribute("company", company);
        return "employees/form";
    }

    @PostMapping("/companies/{companyId}/employees")
    public String saveEmployee(@PathVariable Long companyId,
                               @ModelAttribute EmployeeDTO employeeDTO) {
        employeeService.saveEmployee(employeeDTO, companyId);
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
        EmployeeDTO employeeDTO = employeeService.getEmployeeDTOById(employeeId);
        Company company = companyService.findById(companyId);

        model.addAttribute("company", company);
        model.addAttribute("employee", employeeDTO);
        model.addAttribute("companyId", companyId);
        model.addAttribute("employeeId", employeeId);
        return "employees/form";
    }

    @PostMapping("/companies/{companyId}/employees/{employeeId}")
    public String updateEmployee(@PathVariable Long companyId,
                                 @PathVariable Long employeeId,
                                 @ModelAttribute EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(employeeId, employeeDTO, companyId);
        return "redirect:/companies/" + companyId + "/employees";
    }
}
