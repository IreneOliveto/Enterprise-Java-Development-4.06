package com.exercise.Lab46.controller;

import com.exercise.Lab46.model.Status;
import com.exercise.Lab46.model.Employee;
import com.exercise.Lab46.model.EmployeeUpdateDepartmentDto;
import com.exercise.Lab46.model.EmployeeUpdateStatusDto;
import com.exercise.Lab46.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    //Create a route to get all doctors.
    @GetMapping("/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees() {
        return employeeService.employeeList();
    }

    //Create a route to get a doctor by employee_id.
    @GetMapping("/employee/{employee_id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployeeByEmployeeId(@PathVariable(value="employee_id") int employeeId) {
        return employeeService.getEmployeeByEmployeeId(employeeId);
    }

    //Create a route to get doctors by status.
    @GetMapping("/employees/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeeByStatus(@PathVariable(value="status") Status status) {
        return employeeService.getEmployeesByStatus(status);
    }

    //Get all doctors by department
    @GetMapping("/employees/department/{department}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesByDepartment(@PathVariable(value="department") String department) {
        return employeeService.getEmployeesByDepartment(department);
    }

    //Create a route to add a new doctor.
    @PostMapping("/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    //Create a route to change a doctor’s status.
    @PatchMapping("/employee/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateStatus(@PathVariable String id, @RequestBody EmployeeUpdateStatusDto storedStatus) {
        return employeeService.updateEmployeeStatus(id, storedStatus.getStatus());
    }

    //Create a route to update a doctor’s department.
    @PatchMapping("/employee/{id}/department")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateDepartment(@PathVariable String id, @RequestBody EmployeeUpdateDepartmentDto storedDepartment) {
        return employeeService.updateEmployeeDepartment(id, storedDepartment.getDepartment());
    }

}
