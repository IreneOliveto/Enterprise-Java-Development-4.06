package com.exercise.Lab46.service;

import com.exercise.Lab46.model.Status;
import com.exercise.Lab46.model.Employee;
import com.exercise.Lab46.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> employeeList() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeByEmployeeId(int employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    @Override
    public List<Employee> getEmployeesByStatus(Status status) {
        return employeeRepository.getEmployeeByStatus(status);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.getEmployeeByDepartment(department);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployeeStatus(String id, Status status) {
        Employee storedEmployee = getEmployeeByEmployeeId(Integer.parseInt(id));
        storedEmployee.setStatus(status);
        return employeeRepository.save(storedEmployee);
    }

    @Override
    public Employee updateEmployeeDepartment(String id, String department) {
        Employee storedEmployee = getEmployeeByEmployeeId(Integer.parseInt(id));
        storedEmployee.setDepartment(department);
        return employeeRepository.save(storedEmployee);
    }

}
