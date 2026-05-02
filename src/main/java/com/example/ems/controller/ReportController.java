package com.example.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ems.services.ReportService;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    @GetMapping("/employees")
    public ResponseEntity<?> getEmployeeReport() {
        return ResponseEntity.ok(reportService.generateEmployeeReport());
    }
    
    @GetMapping("/departments")
    public ResponseEntity<?> getDepartmentReport() {
        return ResponseEntity.ok(reportService.generateDepartmentReport());
    }
    
    @GetMapping("/salary")
    public ResponseEntity<?> getSalaryReport() {
        return ResponseEntity.ok(reportService.generateSalaryReport());
    }
}