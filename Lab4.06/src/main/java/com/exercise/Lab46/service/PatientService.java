package com.exercise.Lab46.service;

import com.exercise.Lab46.model.Patient;
import com.exercise.Lab46.model.Status;

import java.util.Date;
import java.util.List;

public interface PatientService {


    List<Patient> patientsList();

    Patient getPatientById(int patientId);

    List<Patient> findAllByDateOfBirthBetween(Date initialDate, Date finalDate);

    List<Patient> findAllPatientsByEmployeeDepartment(String department);

    List<Patient> findAllPatientsByEmployeeStatus(Status status);

    Patient save(Patient patient);
}
