package com.example.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ems.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByType(String type);
    List<Report> findByGeneratedBy(String generatedBy);
}