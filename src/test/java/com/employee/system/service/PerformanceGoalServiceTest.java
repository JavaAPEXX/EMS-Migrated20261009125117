```java
package com.employee.system.service;

import com.employee.system.dto.PerformanceGoalDTO;
import com.employee.system.entity.Employee;
import com.employee.system.entity.PerformanceGoal;
import com.employee.system.repository.EmployeeRepository;
import com.employee.system.repository.PerformanceGoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PerformanceGoalServiceTest {

    @Mock
    private PerformanceGoalRepository goalRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private PerformanceGoalService performanceGoalService;

    @BeforeEach
    public void setUp() {
        // Setup any necessary mocks or configurations
    }

    @DisplayName("Given a valid PerformanceGoalDTO, when createGoal is called, then the goal is created and returned as a DTO")
    @Test
    public void givenValidGoalDTO_whenCreateGoal_isCalled_thenGoalIsCreatedAndReturnedAsDTO() {
        // Arrange
        PerformanceGoalDTO goalDTO = new PerformanceGoalDTO();
        goalDTO.setEmployeeId(1L);
        goalDTO.setGoalTitle("Test Goal");
        goalDTO.setGoalDescription("Test Description");
        goalDTO.setCategory("Test Category");
        goalDTO.setStartDate(LocalDate.now());
        goalDTO.setTargetDate(LocalDate.now().plusDays(10));
        goalDTO.setStatus("ACTIVE");
        goalDTO.setPriority(1);
        goalDTO.setProgressPercentage(50);
        goalDTO.setKeyResults("Test Key Results");
        goalDTO.setRemarks("Test Remarks");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        when(employeeRepository.findById(goalDTO.getEmployeeId())).thenReturn(java.util.Optional.of(employee));
        when(goalRepository.save(any(PerformanceGoal.class))).thenReturn(new PerformanceGoal());

        // Act
        PerformanceGoalDTO result = performanceGoalService.createGoal(goalDTO);

        // Assert
        assertNotNull(result);
        assertEquals(goalDTO.getGoalTitle(), result.getGoalTitle());
        assertEquals(goalDTO.getGoalDescription(), result.getGoalDescription());
        assertEquals(goalDTO.getCategory(), result.getCategory());
        assertEquals(goalDTO.getStartDate(), result.getStartDate());
        assertEquals(goalDTO.getTargetDate(), result.getGoalDescription());
        assertEquals(goalDTO.getStatus(), result.getStatus());
        assertEquals(goalDTO.getPriority(), result.getPriority());
        assertEquals(goalDTO.getProgressPercentage(), result.getProgressPercentage());
        assertEquals(goalDTO.getKeyResults(), result.getKeyResults());
        assertEquals(goalDTO.getRemarks(), result.getRemarks());

        ArgumentCaptor<PerformanceGoal> goalCaptor = ArgumentCaptor.forClass(PerformanceGoal.class);
        verify(goalRepository).save(goalCaptor.capture());
        assertEquals(goalDTO.getGoalTitle(), goalCaptor.getValue().getGoalTitle());
        assertEquals(goalDTO.getGoalDescription(), goalCaptor.getValue().getGoalDescription());
        assertEquals(goalDTO.getCategory(), goalCaptor.getValue().getCategory());
        assertEquals(goalDTO.getStartDate(), goalCaptor.getValue().getStartDate());
        assertEquals(goalDTO.getTargetDate(), goalCaptor.getValue().getGoalDescription());
        assertEquals(goalDTO.getStatus(), goalCaptor.getValue().getStatus());
        assertEquals(goalDTO.getPriority(), goalCaptor.getValue().getPriority());
        assertEquals(goalDTO.getProgressPercentage(), goalCaptor.getValue().getProgressPercentage());
        assertEquals(goalDTO.getKeyResults(), goalCaptor.getValue().getKeyResults());
        assertEquals(goalDTO.getRemarks(), goalCaptor.getValue().getRemarks());
    }

    // Add similar tests for other methods (getGoalById, getGoalsByEmployee