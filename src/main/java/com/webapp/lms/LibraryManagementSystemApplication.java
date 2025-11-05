package com.webapp.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.webapp.lms")                 // scan controllers/services
@EnableJpaRepositories(basePackages = "com.webapp.lms.repository") // scan JPA repositories
@EntityScan(basePackages = "com.webapp.lms.model")                 // scan @Entity classes
public class LibraryManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }
}
