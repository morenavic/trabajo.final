package com.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.miproyecto")
public class InfraestructuraApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfraestructuraApplication.class, args);
    }
}
