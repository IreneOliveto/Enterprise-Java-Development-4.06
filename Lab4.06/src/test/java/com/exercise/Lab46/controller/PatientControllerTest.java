package com.exercise.Lab46.controller;

import com.exercise.Lab46.model.Employee;
import com.exercise.Lab46.model.Patient;
import com.exercise.Lab46.model.Status;
import com.exercise.Lab46.repository.PatientRepository;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PatientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PatientRepository patientRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        Employee employee = new Employee(356712, "cardiology", "Alonso Flores", Status.ON, Collections.emptyList());
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Patient patient1 = new Patient(1, "Jaime Jordan", Date.valueOf("1984-03-02"), employee);
        Patient patient2 = new Patient(2, "Marian Garcia", Date.valueOf("1972-01-12"), employee);

        patientRepository.saveAll(List.of(patient1, patient2));
    }

    @AfterEach
    void tearDown() {
        patientRepository.deleteAll();
    }

    @Test
    void getAllPatients() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Jaime Jordan"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Marian Garcia"));
    }

    @Test
    void getPatientById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/patient/{patient_id}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2"));
    }

    @Test
    void findAllByDateOfBirthBetween() {
    }

    @Test
    void findAllByDepartment() {
    }

    @Test
    void findAllByStatus() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }
}