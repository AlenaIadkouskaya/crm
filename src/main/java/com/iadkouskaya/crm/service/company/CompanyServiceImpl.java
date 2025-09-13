package com.iadkouskaya.crm.service.company;

import com.iadkouskaya.crm.model.entity.Company;
//import com.iadkouskaya.crm.repository.CompanyRepository;
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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {
//    private final CompanyRepository companyRepository;
//    @Value("${upload.dir}")
//    private String uploadDir;
//    private static final int MIN_LOGO_WIDTH = 100;
//    private static final int MIN_LOGO_HEIGHT = 100;
//    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".gif");
//
//    public CompanyServiceImpl(CompanyRepository companyRepository) {
//        this.companyRepository = companyRepository;
//    }
//
//    @Override
//    public Page<Company> findAll(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return companyRepository.findAll(pageable);
//    }
//
//    @Override
//    public void save(Company company) {
//        companyRepository.save(company);
//    }
//
//    @Override
//    public String saveLogo(MultipartFile logoFile) throws IOException {
//        validateLogo(logoFile);
//
//        Path uploadPath = getUploadPath();
//        Files.createDirectories(uploadPath);
//
//        String originalFilename = logoFile.getOriginalFilename();
//        String fileExtension = getFileExtension(originalFilename);
//
//        String newFileName = UUID.randomUUID() + fileExtension;
//        Path filePath = uploadPath.resolve(newFileName);
//
//        logoFile.transferTo(filePath.toFile());
//
//        return "/" + uploadDir.replace("\\", "/") + "/" + newFileName;
//    }
//    private void validateLogo(MultipartFile logoFile) throws IOException {
//        if (logoFile.isEmpty()) {
//            throw new RuntimeException("Plik jest pusty");
//        }
//
//        BufferedImage image = ImageIO.read(logoFile.getInputStream());
//        if (image == null) {
//            throw new RuntimeException("Nieprawidłowy format obrazu");
//        }
//
//        if (image.getWidth() < MIN_LOGO_WIDTH || image.getHeight() < MIN_LOGO_HEIGHT) {
//            throw new RuntimeException("Logo musi mieć minimalny rozmiar 100x100 pikseli");
//        }
//
//        String extension = getFileExtension(logoFile.getOriginalFilename());
//        if (!ALLOWED_EXTENSIONS.contains(extension)) {
//            throw new RuntimeException("Niedozwolony format pliku: " + extension);
//        }
//    }
//    private Path getUploadPath() {
//        return Paths.get(System.getProperty("user.dir")).resolve(uploadDir).toAbsolutePath().normalize();
//    }
//
//    private String getFileExtension(String filename) {
//        if (filename == null || !filename.contains(".")) {
//            return "";
//        }
//        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        companyRepository.deleteById(id);
//    }
//
//    @Override
//    public Optional<Company> findById(Long id) {
//        return companyRepository.findById(id);
//    }
//
//    @Override
//    public long count() {
//        return companyRepository.count();
//    }
//
//    public int getLastPage(int pageSize) {
//        long totalCompanies = count();
//        return (int) ((totalCompanies - 1) / pageSize);
//    }
}
