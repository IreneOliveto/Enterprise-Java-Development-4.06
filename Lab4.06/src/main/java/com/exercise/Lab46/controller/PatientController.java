package com.exercise.Lab46.controller;

import com.exercise.Lab46.model.Patient;
import com.exercise.Lab46.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    //Create a route to get all patients.
    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientService.patientsList();
    }

    //Create a route to get a patient by patient_id.
    @GetMapping("/patient/{patient_id}")
    public Patient getPatientsById(@PathVariable(value="patient_id") int patientId) {
        return patientService.getPatientById(patientId);
    }

    //Create a route to get patients date of birth within a specified range.
    @GetMapping("/patients/{date}")
    public List<Patient> findAllByDateOfBirthBetween(@RequestParam(value = "dateOfBirth") Date initialDate, @RequestParam(value = "dateOfBirth2") Date finalDate){
        return patientService.findAllByDateOfBirthBetween(initialDate, finalDate);
    }

    //Create a route to get patients by the department that their admitting doctor is in
    // (For example, get all patients admitted by a doctor in cardiology).
    @GetMapping("/patients/{department}")
    public List<Patient> findAllPatientsByEmployeeDepartment(@PathVariable(value="department") String department) {
        return patientService.findAllPatientsByEmployeeDepartment(department);
    }

    //Create a route to get all patients with a doctor whose status is OFF.
    @GetMapping("/patients/{status}")
    public List<Patient> findAllByStatus(@PathVariable(value="status") String status) {
        return patientService.findAllPatientsByEmployeeStatus(status);
    }

    //Create a route to add a new patient.
    @PostMapping("/patient")
    public Patient create(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    //Create a route to update patient’s information
    // (the user should be able to update any patient information through this route).
    @PutMapping("/patient")
    public Patient update(@RequestBody Patient patient) {
        return patientService.save(patient);
    }



}
