// TaskRepository.java
package com.example.ems.repository;

import com.example.ems.model.Task;
import com.example.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedTo(Employee employee);
    List<Task> findByStatus(String status);
}