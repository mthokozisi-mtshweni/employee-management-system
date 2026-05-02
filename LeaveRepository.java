// LeaveRepository.java
package com.example.ems.repository;

import com.example.ems.model.Leave;
import com.example.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployee(Employee employee);
    List<Leave> findByStatus(String status);
    List<Leave> findByEmployeeAndStatus(Employee employee, String status);
}