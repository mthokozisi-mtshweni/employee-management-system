package com.example.ems.services;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ems.model.Department;
import com.example.ems.repository.DepartmentRepository;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    public List<Department> getAllDepartments() {
        return departmentRepository.findAllWithEmployees();
    }
    
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }
    
    public Department saveDepartment(Department department) {
        department.setUpdatedAt(LocalDateTime.now());
        return departmentRepository.save(department);
    }
    
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = getDepartmentById(id);
        if (department != null) {
            department.setName(departmentDetails.getName());
            department.setDescription(departmentDetails.getDescription());
            department.setLocation(departmentDetails.getLocation());
            department.setManagerName(departmentDetails.getManagerName());
            department.setBudget(departmentDetails.getBudget());
            department.setPhoneNumber(departmentDetails.getPhoneNumber());
            department.setEmail(departmentDetails.getEmail());
            department.setUpdatedAt(LocalDateTime.now());
            return departmentRepository.save(department);
        }
        return null;
    }
    
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
    
    public Map<String, Object> getDepartmentStatistics() {
        List<Department> departments = getAllDepartments();
        Map<String, Object> stats = new HashMap<>();
        
        int totalEmployees = 0;
        double totalBudget = 0;
        
        for (Department dept : departments) {
            totalEmployees += dept.getEmployeeCount();
            totalBudget += dept.getBudget() != null ? dept.getBudget() : 0;
        }
        
        stats.put("totalDepartments", departments.size());
        stats.put("totalEmployees", totalEmployees);
        stats.put("totalBudget", totalBudget);
        stats.put("averageBudgetPerDepartment", departments.size() > 0 ? totalBudget / departments.size() : 0);
        stats.put("averageEmployeesPerDepartment", departments.size() > 0 ? totalEmployees / departments.size() : 0);
        
        return stats;
    }
}