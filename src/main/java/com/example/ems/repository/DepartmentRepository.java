package com.example.ems.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ems.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
    List<Department> findByLocation(String location);
    
    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees")
    List<Department> findAllWithEmployees();
    
    @Query("SELECT d.name, COUNT(e.id) as empCount, AVG(e.salary) as avgSalary " +
           "FROM Department d LEFT JOIN d.employees e GROUP BY d.id")
    List<Object[]> getDepartmentStatistics();
}