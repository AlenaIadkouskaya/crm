package com.iadkouskaya.crm.service.company;

import com.iadkouskaya.crm.mapper.CompanyMapper;
import com.iadkouskaya.crm.model.entity.dto.CompanyDTO;
import com.iadkouskaya.crm.model.entity.entity.Company;
import com.iadkouskaya.crm.repository.CompanyRepository;
import com.iadkouskaya.crm.service.employee.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final EmployeeService employeeService;
    private final CompanyMapper companyMapper;
    @Value("${upload.dir}")
    private String uploadDir;
    private static final int MIN_LOGO_WIDTH = 100;
    private static final int MIN_LOGO_HEIGHT = 100;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".gif");

    public CompanyServiceImpl(CompanyRepository companyRepository, EmployeeService employeeService, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.employeeService = employeeService;
        this.companyMapper = companyMapper;
    }

    @Override
    public Page<Company> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return companyRepository.findAll(pageable);
    }

    @Override
    public void save(CompanyDTO companyDTO, MultipartFile logoFile) throws IOException {
        CompanyDTO dtoWithLogo = handleLogo(companyDTO, logoFile);

        Company company = toEntity(dtoWithLogo);

        companyRepository.save(company);

    }

    private CompanyDTO handleLogo(CompanyDTO companyDTO, MultipartFile logoFile) throws IOException {
        if (logoFile != null && !logoFile.isEmpty()) {
            String logoPath = saveLogo(logoFile);

            return new CompanyDTO(
                    companyDTO.id(),
                    companyDTO.name(),
                    companyDTO.email(),
                    logoPath,
                    companyDTO.website(),
                    logoFile
            );
        }
        return companyDTO;
    }


    private Company toEntity(CompanyDTO companyDTO) {
        return companyMapper.toEntity(companyDTO);
    }

    @Override
    public String saveLogo(MultipartFile logoFile) throws IOException {
        validateLogo(logoFile);

        Path uploadPath = getUploadPath();
        Files.createDirectories(uploadPath);

        String originalFilename = logoFile.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);

        String newFileName = UUID.randomUUID() + fileExtension;
        Path filePath = uploadPath.resolve(newFileName);

        logoFile.transferTo(filePath.toFile());

        return "/" + uploadDir.replace("\\", "/") + "/" + newFileName;
    }

    private void validateLogo(MultipartFile logoFile) throws IOException {
        if (logoFile.isEmpty()) {
            throw new IllegalArgumentException("Plik jest pusty");
        }

        BufferedImage image = ImageIO.read(logoFile.getInputStream());
        if (image == null) {
            throw new IllegalArgumentException("Nieprawidłowy format obrazu");
        }

        if (image.getWidth() < MIN_LOGO_WIDTH || image.getHeight() < MIN_LOGO_HEIGHT) {
            throw new IllegalArgumentException("Logo musi mieć minimalny rozmiar 100x100 pikseli");
        }

        String extension = getFileExtension(logoFile.getOriginalFilename());
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Niedozwolony format pliku: " + extension);
        }
    }

    private Path getUploadPath() {
        return Paths.get(System.getProperty("user.dir")).resolve(uploadDir).toAbsolutePath().normalize();
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }

    @Override
    public void deleteById(Long id) {

        if (!companyRepository.existsById(id)) {
            throw new EntityNotFoundException("Nie znaleziono firmy do usunięcia o ID " + id);
        }
        long employeesCount = employeeService.countByCompanyId(id);
        if (employeesCount > 0) {
            throw new IllegalStateException("Nie można usunąć firmy, ponieważ ma przypisanych pracowników.");
        }
        companyRepository.deleteById(id);
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invalid company Id:" + id));
    }

    @Override
    public long count() {
        return companyRepository.count();
    }

    public int getLastPage(int pageSize) {
        long totalCompanies = count();
        return (int) ((totalCompanies - 1) / pageSize);
    }

    @Override
    public boolean isValid(CompanyDTO company) {
        return company.name() != null && !company.name().isEmpty();
    }

    private void updateExistingCompany(Company updated) {
        Company existing = companyRepository.findById(updated.getId())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono firmy o ID " + updated.getId()));

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setWebsite(updated.getWebsite());

        if (updated.getLogo() != null) {
            existing.setLogo(updated.getLogo());
        }

        companyRepository.save(existing);
    }
    @Override
    public CompanyDTO getCompanyDTOById(Long id) {
        Company company = findById(id);
        return companyMapper.toDTO(company);
    }
    @Override
    @Transactional
    public void update(Long id, CompanyDTO companyDTO, MultipartFile logoFile) throws IOException {
        Company company = findExistingCompany(id);
        updateCompanyFields(company, companyDTO);
        updateCompanyLogo(company, logoFile);
        companyRepository.save(company);
    }

    private Company findExistingCompany(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono firmy o ID: " + id));
    }

    private void updateCompanyFields(Company company, CompanyDTO dto) {
        company.setName(dto.name());
        company.setEmail(dto.email());
        company.setWebsite(dto.website());
    }

    private void updateCompanyLogo(Company company, MultipartFile logoFile) throws IOException {
        if (logoFile != null && !logoFile.isEmpty()) {
            String logoPath = saveLogo(logoFile);
            company.setLogo(logoPath);
        }
    }
}
