package com.example.ems.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String type; // EMPLOYEE, DEPARTMENT, SALARY, LEAVE, PERFORMANCE
    private String format; // PDF, EXCEL, CSV
    private String filePath;
    private String generatedBy;
    private String parameters;
    
    @Lob
    private String data;
    
    private LocalDateTime generatedAt;
    private LocalDateTime dateRangeStart;
    private LocalDateTime dateRangeEnd;
    
    public Report() {
        this.generatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public String getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(String generatedBy) { this.generatedBy = generatedBy; }
    
    public String getParameters() { return parameters; }
    public void setParameters(String parameters) { this.parameters = parameters; }
    
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
    
    public LocalDateTime getDateRangeStart() { return dateRangeStart; }
    public void setDateRangeStart(LocalDateTime dateRangeStart) { this.dateRangeStart = dateRangeStart; }
    
    public LocalDateTime getDateRangeEnd() { return dateRangeEnd; }
    public void setDateRangeEnd(LocalDateTime dateRangeEnd) { this.dateRangeEnd = dateRangeEnd; }
}