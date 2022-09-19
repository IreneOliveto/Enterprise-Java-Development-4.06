package com.exercise.Lab46.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name="employee")
@DynamicUpdate
public class Employee {
    @Id
    private Integer employeeId;
    private String department;
    private String name;

    @NotNull
    @Enumerated(STRING)
    @Column(nullable = false)
    private Status status;

    @OneToMany(mappedBy="admittedBy")
    @JsonIgnore
    private List<Patient> patientsList;

    public Employee(Integer employeeId, String department, String name, Status status, List<Patient> patientsList) {
        this.employeeId = employeeId;
        this.department = department;
        this.name = name;
        this.status = status;
        this.patientsList = patientsList;
    }

    public Employee() {
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Patient> getPatientsList() {
        return patientsList;
    }

    public void setPatientsList(List<Patient> patientsList) {
        this.patientsList = patientsList;
    }
}
