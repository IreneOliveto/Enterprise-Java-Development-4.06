package com.exercise.Lab46.controller;

import com.exercise.Lab46.model.Patient;
import com.exercise.Lab46.model.Status;
import com.exercise.Lab46.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    //Create a route to get all patients.
    @GetMapping("/patients")
    @ResponseStatus(HttpStatus.OK)
    public List<Patient> getAllPatients() {
        return patientService.patientsList();
    }

    //Create a route to get a patient by patient_id.
    @GetMapping("/patient/id/{patient_id}")
    @ResponseStatus(HttpStatus.OK)
    public Patient getPatientsById(@PathVariable(value="patient_id") int patientId) {
        return patientService.getPatientById(patientId);
    }

    //Create a route to get patients date of birth within a specified range.
    @GetMapping("/patient/id/between-date-of-birth")
    @ResponseStatus(HttpStatus.OK)
    public List<Patient> findAllByDateOfBirthBetween(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date initialDate, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date finalDate){
        return patientService.findAllByDateOfBirthBetween(initialDate, finalDate);
    }

    //Create a route to get patients by the department that their admitting doctor is in
    // (For example, get all patients admitted by a doctor in cardiology).
    @GetMapping("/patients/department/{department}")
    @ResponseStatus(HttpStatus.OK)
    public List<Patient> findAllPatientsByEmployeeDepartment(@PathVariable(value="department") String department) {
        return patientService.findAllPatientsByEmployeeDepartment(department);
    }

    //Create a route to get all patients with a doctor whose status is OFF.
    @GetMapping("/patients/status/OFF")
    @ResponseStatus(HttpStatus.OK)
    public List<Patient> findAllPatientsByEmployeeStatus() {
        return patientService.findAllPatientsByEmployeeStatus(Status.OFF);
    }

    //Create a route to add a new patient.
    @PostMapping("/patient")
    @ResponseStatus(HttpStatus.CREATED)
    public Patient create(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    //Create a route to update patient’s information
    // (the user should be able to update any patient information through this route).
    @PutMapping("/patient")
    @ResponseStatus(HttpStatus.OK)
    public Patient update(@RequestBody Patient patient) {
        return patientService.save(patient);
    }



}
