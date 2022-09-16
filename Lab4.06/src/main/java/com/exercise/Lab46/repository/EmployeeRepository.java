package com.exercise.Lab46.repository;

import com.exercise.Lab46.model.Employee;
import com.exercise.Lab46.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    List<Employee> getEmployeeByStatus(Status status);

    List<Employee> getEmployeeByDepartment(String department);
}
