package com.exercise.Lab46.repository;

import com.exercise.Lab46.model.Patient;
import com.exercise.Lab46.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findByDateOfBirthBetween(Date initialDate, Date finalDate);

    @Query(value = "SELECT p.patient_id, p.name, p.date_of_birth, p.employee_id FROM patient p left join employee e on p.employee_id = e.employee_id WHERE e.department = :department",
            nativeQuery = true)
    List<Patient> findAllPatientsByEmployeeDepartment(@Param("department") String department);

    @Query(value = "SELECT p.patient_id, p.name, p.date_of_birth, p.employee_id FROM patient p left join employee e on p.employee_id = e.employee_id WHERE e.status = :status",
            nativeQuery = true)
    List<Patient> findAllPatientsByEmployeeStatus(@Param("status") String status);
}
