package com.exercise.Lab46.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="patient")
public class Patient {

    @Id
    private int patientId;
    private String name;
    private Date dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="employee_id")
    private Employee admittedBy;

    public Patient(int patientId, String name, Date dateOfBirth, Employee admittedBy) {
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.admittedBy = admittedBy;
    }

    public Patient() {
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Employee getAdmittedBy() {
        return admittedBy;
    }

    public void setAdmittedBy(Employee admittedBy) {
        this.admittedBy = admittedBy;
    }
}

