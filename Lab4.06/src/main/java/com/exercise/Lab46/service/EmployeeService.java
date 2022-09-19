package com.exercise.Lab46.service;

import com.exercise.Lab46.model.Status;
import com.exercise.Lab46.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> employeeList();
    Employee getEmployeeByEmployeeId(int employeeId);

    List<Employee> getEmployeesByStatus(Status status);

    List<Employee> getEmployeesByDepartment(String department);
    Employee save(Employee employee);

    Employee updateEmployeeStatus(String id, Status status);
    Employee updateEmployeeDepartment(String id, String department);
}