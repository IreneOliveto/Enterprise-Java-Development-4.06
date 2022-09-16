package com.exercise.Lab46.controller;

import com.exercise.Lab46.model.Status;
import com.exercise.Lab46.model.Employee;
import com.exercise.Lab46.model.EmployeeUpdateDepartmentDto;
import com.exercise.Lab46.model.EmployeeUpdateStatusDto;
import com.exercise.Lab46.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    //Create a route to get all doctors.
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.employeeList();
    }

    //Create a route to get a doctor by employee_id.
    @GetMapping("/employee/{employee_id}")
    public Employee getEmployeeByEmployeeId(@PathVariable(value="employee_id") int employeeId) {
        return employeeService.getEmployeeByEmployeeId(employeeId);
    }

    //Create a route to get doctors by status.
    @GetMapping("/employees/{status}")
    public Employee getEmployeeByStatus(@PathVariable(value="status") Status status) {
        return (Employee) employeeService.getEmployeeByStatus(status);
    }

    //Get all doctors by department
    @GetMapping("/employees/{department}")
    public Employee getEmployeeByDepartment(@PathVariable(value="department") String department) {
        return (Employee) employeeService.getEmployeeByDepartment(department);
    }

    //Create a route to add a new doctor.
    @PostMapping("/employees")
    public Employee create(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    //Create a route to change a doctor’s status.
    @PatchMapping("/employee/{id}/status")
    public Employee updateStatus(@PathVariable String id, @RequestBody EmployeeUpdateStatusDto storedStatus) {
        return employeeService.updateEmployeeStatus(id, storedStatus.getStatus());
    }

    //Create a route to update a doctor’s department.
    @PatchMapping("/employee/{id}/department")
    public Employee updateDepartment(@PathVariable String id, @RequestBody EmployeeUpdateDepartmentDto storedDepartment) {
        return employeeService.updateEmployeeDepartment(id, storedDepartment.getDepartment());
    }

}
