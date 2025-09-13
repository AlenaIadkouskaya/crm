package com.iadkouskaya.crm.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class EnvLogger {
    private static final Logger logger = LoggerFactory.getLogger(EnvLogger.class);

    @Value("${spring.datasource.url:NOT SET}")
    private String datasourceUrl;

    @Value("${spring.datasource.username:NOT SET}")
    private String datasourceUsername;

    @Value("${spring.datasource.password:NOT SET}")
    private String datasourcePassword;

    @PostConstruct
    public void logEnv() {
        System.out.println("spring.datasource.url = " + datasourceUrl);
        System.out.println("spring.username.url = " + datasourceUsername);
        System.out.println("spring.password.url = " + datasourcePassword);
        logger.info("spring.datasource.url = {}", datasourceUrl);
        logger.info("spring.datasource.username = {}", datasourceUsername);
        logger.info("spring.datasource.password = {}", datasourcePassword.equals("NOT SET") ? "NOT SET" : "***");
    }
}
