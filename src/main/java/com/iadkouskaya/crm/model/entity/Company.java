package com.iadkouskaya.crm.model.entity;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

//@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String email;

    private String logo;

    private String website;
    @Transient
    private MultipartFile logoFile;

    public Company() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLogo() {
        return logo;
    }

    public String getWebsite() {
        return website;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public MultipartFile getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(MultipartFile logoFile) {
        this.logoFile = logoFile;
    }
}
