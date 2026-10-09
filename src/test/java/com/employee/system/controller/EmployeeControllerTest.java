```java
package com.employee.system.controller;

import com.employee.system.dto.EmployeeDTO;
import com.employee.system.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Given a valid employee DTO, when creating an employee, then the created employee should be returned with a 201 status code")
    @Test
    public void givenValidEmployeeDTO_whenCreatingEmployee_thenCreatedEmployeeIsReturnedWith201StatusCode() {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setDepartment("Engineering");
        employeeDTO.setDesignation("Software Engineer");

        EmployeeDTO expectedEmployee = new EmployeeDTO();
        expectedEmployee.setId(1L);
        expectedEmployee.setName("John Doe");
        expectedEmployee.setDepartment("Engineering");
        expectedEmployee.setDesignation("Software Engineer");

        when(employeeService.createEmployee(employeeDTO)).thenReturn(expectedEmployee);

        // Act
        ResponseEntity<EmployeeDTO> response = employeeController.createEmployee(employeeDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedEmployee, response.getBody());
        verify(employeeService, times(1)).createEmployee(employeeDTO);
    }

    @DisplayName("Given an existing employee ID, when getting an employee by ID, then the employee should be returned")
    @Test
    public void givenExistingEmployeeId_whenGettingEmployeeById_thenEmployeeIsReturned() {
        // Arrange
        Long id = 1L;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(id);
        employeeDTO.setName("John Doe");
        employeeDTO.setDepartment("Engineering");
        employeeDTO.setDesignation("Software Engineer");

        when(employeeService.getEmployeeById(id)).thenReturn(employeeDTO);

        // Act
        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDTO, response.getBody());
        verify(employeeService, times(1)).getEmployeeById(id);
    }

    @DisplayName("Given an existing employee ID, when getting an employee by employee ID, then the employee should be returned")
    @Test
    public void givenExistingEmployeeId_whenGettingEmployeeByEmployeeId_thenEmployeeIsReturned() {
        // Arrange
        String employeeId = "12345";
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");
        employeeDTO.setDepartment("Engineering");
        employeeDTO.setDesignation("Software Engineer");

        when(employeeService.getEmployeeByEmployeeId(employeeId)).thenReturn(employeeDTO);

        // Act
        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeByEmployeeId(employeeId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDTO, response.getBody());
        verify(employeeService, times(1)).getEmployeeByEmployeeId(employeeId);
    }

    @DisplayName("Given a list of employees, when getting all employees, then the list of employees should be returned")
    @Test
    public void givenListOfEmployees_whenGettingAllEmployees_thenListOfEmployeesIsReturned() {
        // Arrange
        List<EmployeeDTO> employees = List.of