package com.exercise.Lab46.service;

import com.exercise.Lab46.model.Patient;
import com.exercise.Lab46.model.Status;
import com.exercise.Lab46.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public List<Patient> patientsList() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(int patientId) {
        return patientRepository.findById(patientId).get();
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> findAllByDateOfBirthBetween(Date initialDate, Date finalDate) {
        return patientRepository.findByDateOfBirthBetween(initialDate, finalDate);
    }

    @Override
    public List<Patient> findAllPatientsByEmployeeDepartment(String department) {
        return patientRepository.findAllPatientsByEmployeeDepartment(department);
    }

    @Override
    public List<Patient> findAllPatientsByEmployeeStatus(Status status) {
        return patientRepository.findAllPatientsByEmployeeStatus(status.toString());
    }


}