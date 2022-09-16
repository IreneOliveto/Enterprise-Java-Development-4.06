package com.exercise.Lab46.controller;

import com.exercise.Lab46.model.Employee;
import com.exercise.Lab46.model.Patient;
import com.exercise.Lab46.model.Status;
import com.exercise.Lab46.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class EmployeeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private EmployeeRepository employeeRepository;

     private MockMvc mockMvc;
     private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        List<Patient> patients = emptyList();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Employee employee1 = new Employee(356712, "cardiology", "Alonso Flores", Status.ON, patients);
        Employee employee2 = new Employee(564134, "immunology", "Sam Ortega", Status.ON, patients);

        employeeRepository.saveAll(List.of(employee1, employee2));
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void getAllEmployees() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Alonso Flores"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sam Ortega"));
    }

    @Test
    void getEmployeeByEmployeeId() {
    }

    @Test
    void getEmployeeByStatus() {
    }

    @Test
    void getEmployeeByDepartment() {
    }

    @Test
    void create() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void updateDepartment() {
    }
}