package com.example.ems.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ems.model.Department;
import com.example.ems.model.Employee;
import com.example.ems.model.Report;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.repository.ReportRepository;

@Service
public class ReportService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private ReportRepository reportRepository;
    
    public Map<String, Object> generateEmployeeReport() {
        List<Employee> employees = employeeRepository.findAll();
        Map<String, Object> report = new HashMap<>();
        
        report.put("reportType", "EMPLOYEE_REPORT");
        report.put("generatedAt", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        report.put("totalEmployees", employees.size());
        
        // Department distribution
        Map<String, Integer> deptDistribution = new HashMap<>();
        Map<String, Double> deptSalarySum = new HashMap<>();
        
        for (Employee emp : employees) {
            String dept = emp.getDepartmentName();
            deptDistribution.put(dept, deptDistribution.getOrDefault(dept, 0) + 1);
            deptSalarySum.put(dept, deptSalarySum.getOrDefault(dept, 0.0) + emp.getSalary());
        }
        
        report.put("departmentDistribution", deptDistribution);
        report.put("departmentSalarySum", deptSalarySum);
        
        // Salary statistics
        DoubleSummaryStatistics salaryStats = employees.stream()
            .mapToDouble(Employee::getSalary)
            .summaryStatistics();
        
        report.put("minSalary", salaryStats.getMin());
        report.put("maxSalary", salaryStats.getMax());
        report.put("avgSalary", salaryStats.getAverage());
        report.put("totalSalary", salaryStats.getSum());
        
        // Status distribution
        Map<String, Integer> statusDistribution = new HashMap<>();
        for (Employee emp : employees) {
            statusDistribution.put(emp.getStatus(), statusDistribution.getOrDefault(emp.getStatus(), 0) + 1);
        }
        report.put("statusDistribution", statusDistribution);
        
        // Employees list for detailed report
        List<Map<String, Object>> employeeDetails = new ArrayList<>();
        for (Employee emp : employees) {
            Map<String, Object> details = new HashMap<>();
            details.put("id", emp.getId());
            details.put("name", emp.getFullName());
            details.put("email", emp.getEmail());
            details.put("department", emp.getDepartmentName());
            details.put("position", emp.getPosition());
            details.put("salary", emp.getSalary());
            details.put("status", emp.getStatus());
            details.put("hireDate", emp.getHireDate());
            employeeDetails.add(details);
        }
        report.put("employees", employeeDetails);
        
        return report;
    }
    
    public Map<String, Object> generateDepartmentReport() {
        List<Department> departments = departmentRepository.findAllWithEmployees();
        Map<String, Object> report = new HashMap<>();
        
        report.put("reportType", "DEPARTMENT_REPORT");
        report.put("generatedAt", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        report.put("totalDepartments", departments.size());
        
        List<Map<String, Object>> deptDetails = new ArrayList<>();
        double totalBudget = 0;
        int totalEmployees = 0;
        
        for (Department dept : departments) {
            Map<String, Object> details = new HashMap<>();
            details.put("id", dept.getId());
            details.put("name", dept.getName());
            details.put("description", dept.getDescription());
            details.put("location", dept.getLocation());
            details.put("manager", dept.getManagerName());
            details.put("budget", dept.getBudget());
            details.put("employeeCount", dept.getEmployeeCount());
            details.put("phone", dept.getPhoneNumber());
            details.put("email", dept.getEmail());
            
            deptDetails.add(details);
            totalBudget += dept.getBudget() != null ? dept.getBudget() : 0;
            totalEmployees += dept.getEmployeeCount();
        }
        
        report.put("departments", deptDetails);
        report.put("totalBudget", totalBudget);
        report.put("totalEmployees", totalEmployees);
        report.put("averageBudgetPerDepartment", departments.size() > 0 ? totalBudget / departments.size() : 0);
        
        return report;
    }
    
    public Map<String, Object> generateSalaryReport() {
        List<Employee> employees = employeeRepository.findAll();
        Map<String, Object> report = new HashMap<>();
        
        report.put("reportType", "SALARY_REPORT");
        report.put("generatedAt", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        // Salary brackets
        Map<String, Integer> salaryBrackets = new LinkedHashMap<>();
        salaryBrackets.put("Below 50k", 0);
        salaryBrackets.put("50k - 70k", 0);
        salaryBrackets.put("70k - 90k", 0);
        salaryBrackets.put("90k - 110k", 0);
        salaryBrackets.put("Above 110k", 0);
        
        for (Employee emp : employees) {
            double salary = emp.getSalary();
            if (salary < 50000) salaryBrackets.put("Below 50k", salaryBrackets.get("Below 50k") + 1);
            else if (salary < 70000) salaryBrackets.put("50k - 70k", salaryBrackets.get("50k - 70k") + 1);
            else if (salary < 90000) salaryBrackets.put("70k - 90k", salaryBrackets.get("70k - 90k") + 1);
            else if (salary < 110000) salaryBrackets.put("90k - 110k", salaryBrackets.get("90k - 110k") + 1);
            else salaryBrackets.put("Above 110k", salaryBrackets.get("Above 110k") + 1);
        }
        
        report.put("salaryBrackets", salaryBrackets);
        
        // Average salary by department
        Map<String, Double> avgSalaryByDept = new HashMap<>();
        Map<String, Integer> countByDept = new HashMap<>();
        
        for (Employee emp : employees) {
            String dept = emp.getDepartmentName();
            avgSalaryByDept.put(dept, avgSalaryByDept.getOrDefault(dept, 0.0) + emp.getSalary());
            countByDept.put(dept, countByDept.getOrDefault(dept, 0) + 1);
        }
        
        for (String dept : avgSalaryByDept.keySet()) {
            avgSalaryByDept.put(dept, avgSalaryByDept.get(dept) / countByDept.get(dept));
        }
        
        report.put("averageSalaryByDepartment", avgSalaryByDept);
        
        // Top earners
        List<Employee> topEarners = employees.stream()
            .sorted((a, b) -> Double.compare(b.getSalary(), a.getSalary()))
            .limit(10)
            .toList();
        
        List<Map<String, Object>> topEarnersList = new ArrayList<>();
        for (Employee emp : topEarners) {
            Map<String, Object> earner = new HashMap<>();
            earner.put("name", emp.getFullName());
            earner.put("department", emp.getDepartmentName());
            earner.put("position", emp.getPosition());
            earner.put("salary", emp.getSalary());
            topEarnersList.add(earner);
        }
        report.put("topEarners", topEarnersList);
        
        return report;
    }
    
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }
    
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
}
