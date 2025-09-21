package com.iadkouskaya.crm.controller;

import com.iadkouskaya.crm.model.entity.dto.CompanyDTO;
import com.iadkouskaya.crm.model.entity.entity.Company;
import com.iadkouskaya.crm.service.company.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/companies")

public class CompanyController {
    private final CompanyService companyService;
    @Value("${app.pagination.page-size}")
    private int pageSize;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        CompanyDTO companyDTO = new CompanyDTO(null, null, null, null, null, null);
        model.addAttribute("company", companyDTO);
        return "companies/form";
    }

    @GetMapping
    public String getAllCompanies(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Company> companiesPage = companyService.findAll(page, pageSize);

        model.addAttribute("companiesPage", companiesPage);
        return "companies/list";
    }

    @PostMapping
    public String saveCompany(@ModelAttribute CompanyDTO companyDTO,
                              @RequestParam("logoFile") MultipartFile logoFile,
                              Model model) {

        if (!companyService.isValid(companyDTO)) {
            model.addAttribute("company", companyDTO);
            return "companies/form";
        }

        try {
            companyService.save(companyDTO, logoFile);
        } catch (Exception e) {
            model.addAttribute("company", companyDTO);
            model.addAttribute("errorMessage", e.getMessage());
            return "companies/form";
        }

        return "redirect:/companies?page=" + companyService.getLastPage(pageSize);
    }

    @PostMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Long id,
                                @RequestParam(defaultValue = "0") int page,
                                RedirectAttributes redirectAttributes) {
        try {
            companyService.deleteById(id);
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nie znaleziono firmy o ID: " + id);
            return "redirect:/companies?page=" + page;
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/companies?page=" + page;
        }
        return "redirect:/companies?page=" + page;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CompanyDTO companyDTO = companyService.getCompanyDTOById(id);
        model.addAttribute("company", companyDTO);
        return "companies/form";
    }
    @PostMapping("/{id}")
    public String updateCompany(@PathVariable Long id,
                                @ModelAttribute CompanyDTO companyDTO,
                                @RequestParam("logoFile") MultipartFile logoFile,
                                Model model) {

        if (!companyService.isValid(companyDTO)) {
            model.addAttribute("company", companyDTO);
            return "companies/form";
        }

        try {
            companyService.update(id, companyDTO, logoFile);
        } catch (Exception e) {
            model.addAttribute("company", companyDTO);
            model.addAttribute("errorMessage", e.getMessage());
            return "companies/form";
        }

        return "redirect:/companies?page=" + companyService.getLastPage(pageSize);
    }

}
