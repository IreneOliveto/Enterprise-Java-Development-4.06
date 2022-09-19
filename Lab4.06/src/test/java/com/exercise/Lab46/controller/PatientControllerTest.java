package com.exercise.Lab46.controller;

import com.exercise.Lab46.model.Employee;
import com.exercise.Lab46.model.EmployeeUpdateStatusDto;
import com.exercise.Lab46.model.Patient;
import com.exercise.Lab46.model.Status;
import com.exercise.Lab46.repository.EmployeeRepository;
import com.exercise.Lab46.repository.PatientRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.exercise.Lab46.model.Status.OFF;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PatientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Employee employee1 = new Employee(100, "immunology", "Sam Ortega", Status.ON, Collections.emptyList());
        employeeRepository.save(employee1);
        Patient patient1 = new Patient(6, "patient1", Date.valueOf("1998-12-01"), employee1);
        Patient patient2 = new Patient(7, "patient2", Date.valueOf("1986-12-01"), employee1);

        patientRepository.saveAll(List.of(patient1, patient2));
    }

    @AfterEach
    void tearDown() {
        patientRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void getAllPatients() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<Patient> patients = objectMapper.readValue(mvcResult.getRequest().getContentAsString(), new TypeReference<List<Patient>>() {});

        assertEquals(2, patients.size());

        assertTrue(mvcResult.getResponse().getContentAsString().contains("patient1"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("patient2"));
    }

    @Test
    void getPatientById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/patient/id/6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Patient patient = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Patient.class);

        assertEquals(patient.getPatientId(), 6);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("patient1"));
    }

    @Test
    void findAllByDateOfBirthBetween() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/patient/id/between-date-of-birth")
                        .param("initialDate", "1990-12-01")
                        .param("finalDate", "2000-12-01")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<Patient> patients = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Patient>>() {});

        assertTrue(mvcResult.getResponse().getContentAsString().contains("patient1"));
    }

    @Test
    void findAllPatientsByEmployeeDepartment() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/patients/department/immunology"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Patient> patients = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Patient>>(){});

        assertEquals(2, patients.size());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("patient1"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("patient2"));
    }

    @Test
    void findAllPatientsByEmployeeStatus() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/patients/status/OFF"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Patient> patients = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Patient>>(){});

        assertTrue(patients.isEmpty());
        assertFalse(mvcResult.getResponse().getContentAsString().contains("patient1"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("patient2"));
    }

    @Test
    void create() throws Exception {
        Employee employee = new Employee(3434, "Oncology", "Paolo", Status.ON_CALL, emptyList());
        employeeRepository.save(employee);
        Patient newPatient = new Patient(666, "newPatient", Date.valueOf("1997-12-01"), employee);
        String payload = (objectMapper.writeValueAsString(newPatient));

        MvcResult mvcResult = mockMvc.perform(post("/patient")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Patient patient = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Patient.class);

        assertEquals(patient.getName(), "newPatient");
        assertEquals(patient.getPatientId(), 666);

        assertTrue(patientRepository.findById(666).isPresent());
    }

    @Test
    void update() throws Exception {
        Employee employee = new Employee(3434, "Oncology", "Paolo", Status.ON_CALL, emptyList());
        employeeRepository.save(employee);
        Patient oldPatient = new Patient(666, "oldPatient", Date.valueOf("1997-12-01"), employee);
        patientRepository.save(oldPatient);
        Patient newPatient = new Patient(666, "newPatient", Date.valueOf("1997-12-01"), employee);

        String payload = (objectMapper.writeValueAsString(newPatient));

        MvcResult mvcResult = mockMvc.perform(
                        put("/patient")
                                .content(payload)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Patient patient = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Patient.class);

        assertEquals(patient.getName(), "newPatient");
    }
}