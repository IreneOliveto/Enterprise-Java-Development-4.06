package com.exercise.Lab46.service;

import com.exercise.Lab46.model.Patient;

import java.util.Date;
import java.util.List;

public interface PatientService {


    List<Patient> patientsList();

    Patient getPatientById(int patientId);

    List<Patient> findAllByDateOfBirthBetween(Date dateOfBirth, Date dateOfBirth2);

    List<Patient> findAllPatientsByEmployeeDepartment(String department);

    List<Patient> findAllPatientsByEmployeeStatus(String status);

    Patient save(Patient patient);
}
