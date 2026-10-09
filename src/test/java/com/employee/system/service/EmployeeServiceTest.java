```java
package com.employee.system.service;

import com.employee.system.dto.EmployeeDTO;
import com.employee.system.entity.Employee;
import com.employee.system.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(employeeService, "employeeRepository", employeeRepository);
    }

    // Test createEmployee
    @Test
    @DisplayName("Given valid employee DTO when createEmployee is called then return created employee DTO")
    public void givenValidEmployeeDTO_whenCreateEmployee_thenReturnCreatedEmployeeDTO() {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId("E001");
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setPhoneNumber("1234567890");
        employeeDTO.setDepartment("HR");
        employeeDTO.setDesignation("Manager");
        employeeDTO.setDateOfJoining(LocalDate.now());
        employeeDTO.setStatus("ACTIVE");
        employeeDTO.setAddress("123 Main St");
        employeeDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employeeDTO.setGender("Male");

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setEmployeeId("E001");
        expectedEmployee.setFirstName("John");
        expectedEmployee.setLastName("Doe");
        expectedEmployee.setEmail("john.doe@example.com");
        expectedEmployee.setPhoneNumber("1234567890");
        expectedEmployee.setDepartment("HR");
        expectedEmployee.setDesignation("Manager");
        expectedEmployee.setDateOfJoining(LocalDate.now());
        expectedEmployee.setStatus("ACTIVE");
        expectedEmployee.setAddress("123 Main St");
        expectedEmployee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        expectedEmployee.setGender("Male");

        when(employeeRepository.save(expectedEmployee)).thenReturn(expectedEmployee);

        // Act
        EmployeeDTO result = employeeService.createEmployee(employeeDTO);

        // Assert
        assertEquals(expectedEmployee.getId(), result.getId());
        assertEquals(expectedEmployee.getEmployeeId(), result.getEmployeeId());
        assertEquals(expectedEmployee.getFirstName(), result.getFirstName());
        assertEquals(expectedEmployee.getLastName(), result.getLastName());
        assertEquals(expectedEmployee.getEmail(), result.getEmail());
        assertEquals(expectedEmployee.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(expectedEmployee.getDepartment(), result.getDepartment());
        assertEquals(expectedEmployee.getDesignation(), result.getDesignation());
        assertEquals(expectedEmployee.getDateOfJoining(), result.getDateOfJoining());
        assertEquals(expectedEmployee.getStatus(), result.getStatus());
        assertEquals(expectedEmployee.getAddress(), result.getAddress());
        assertEquals(expectedEmployee.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(expectedEmployee.getGender(), result.getGender());
        verify(employeeRepository, times(1)).save(expectedEmployee);
    }

    // Test getEmployeeById
    @Test
    @DisplayName("Given existing employee ID when getEmployeeById is called then return employee DTO")
    public void givenExistingEmployeeId_whenGetEmployeeById_thenReturnEmployeeDTO() {
        // Arrange
        Long id = 1L;
        Employee employee = new Employee();
        employee