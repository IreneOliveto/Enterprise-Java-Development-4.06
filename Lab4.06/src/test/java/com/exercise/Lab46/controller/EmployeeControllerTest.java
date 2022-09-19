package com.exercise.Lab46.controller;

import com.exercise.Lab46.model.*;
import com.exercise.Lab46.repository.EmployeeRepository;
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

import java.util.List;

import static com.exercise.Lab46.model.Status.OFF;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        List<Patient> patients = emptyList();
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
        List<Employee> employees = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Employee>>() {});

        assertEquals(2, employees.size());

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Alonso Flores"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sam Ortega"));
    }

    @Test
    void getEmployeeByEmployeeId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employee/356712"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Employee employee = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class);

        assertEquals(employee.getName(), "Alonso Flores");
        assertEquals(employee.getDepartment(), "cardiology");
    }

    @Test
    void getEmployeeByStatus() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employees/status/ON"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Employee> employees = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Employee>>() {});

        assertEquals(2, employees.size());
        assertEquals("Sam Ortega", employees.get(0).getName());
        assertEquals("Alonso Flores", employees.get(1).getName());

    }

    @Test
    void getEmployeeByDepartment() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employees/department/immunology"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Employee> employees = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Employee>>(){});

        assertEquals(employees.get(0).getName(), "Sam Ortega");
        assertEquals(employees.get(0).getDepartment(), "immunology");
    }

    @Test
    void create() throws Exception {
        List<Patient> patients = emptyList();
        Employee newEmployee = new Employee(3434, "Oncology", "Paolo", Status.ON_CALL, patients);
        String payload = (objectMapper.writeValueAsString(newEmployee));

        MvcResult mvcResult = mockMvc.perform(post("/employee")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Employee employee = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class);

        assertEquals(employee.getName(), "Paolo");
        assertEquals(employee.getDepartment(), "Oncology");

        assertTrue(employeeRepository.findById(3434).isPresent());
    }

    @Test
    void updateStatus() throws Exception {
        EmployeeUpdateStatusDto newStatus = new EmployeeUpdateStatusDto(Status.OFF);
        String payload = (objectMapper.writeValueAsString(newStatus));

        MvcResult mvcResult = mockMvc.perform(
                patch("/employee/564134/status")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Employee employee = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class);

        assertEquals(employee.getStatus(), OFF);
    }

    @Test
    void updateDepartment() throws Exception {
        EmployeeUpdateDepartmentDto newDepartment = new EmployeeUpdateDepartmentDto("immunology");
        String payload = (objectMapper.writeValueAsString(newDepartment));

        MvcResult mvcResult = mockMvc.perform(
                        patch("/employee/356712/department")
                                .content(payload)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Employee employee = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class);

        assertEquals(employee.getDepartment(), "immunology");
    }
}