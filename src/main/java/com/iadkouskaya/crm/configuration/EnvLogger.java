package com.iadkouskaya.crm.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvLogger {
    @Value("${SPRING_DATASOURCE_URL:NOT SET}")
    private String datasourceUrl;

    @Value("${SPRING_DATASOURCE_USERNAME:NOT SET}")
    private String datasourceUsername;

    @Value("${SPRING_DATASOURCE_PASSWORD:NOT SET}")
    private String datasourcePassword;

    @PostConstruct
    public void logEnv() {
        System.out.println("SPRING_DATASOURCE_URL = " + datasourceUrl);
        System.out.println("SPRING_DATASOURCE_USERNAME = " + datasourceUsername);
        System.out.println("SPRING_DATASOURCE_PASSWORD = " + (datasourcePassword.equals("NOT SET") ? "NOT SET" : "***"));
    }
}
