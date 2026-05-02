package com.example.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmsApplication.class, args);
        System.out.println("========================================");
        System.out.println("Employee Management System Started!");
        System.out.println("Access at: http://localhost:8080");
        System.out.println("========================================");
    }
}