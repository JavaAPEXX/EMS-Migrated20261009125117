```java
package com.employee.system.service;

import com.employee.system.dto.ManagerFeedbackDTO;
import com.employee.system.entity.Employee;
import com.employee.system.entity.ManagerFeedback;
import com.employee.system.repository.EmployeeRepository;
import com.employee.system.repository.ManagerFeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class ManagerFeedbackServiceTest {

    @InjectMocks
    private ManagerFeedbackService managerFeedbackService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ManagerFeedbackRepository feedbackRepository;

    private ManagerFeedbackDTO feedbackDTO;

    @BeforeEach
    public void setUp() {
        feedbackDTO = new ManagerFeedbackDTO();
        feedbackDTO.setEmployeeId(1L);
        feedbackDTO.setProvidedById(2L);
        feedbackDTO.setFeedbackType("Performance");
        feedbackDTO.setFeedbackCategory("Performance Review");
        feedbackDTO.setFeedbackText("Great job!");
        feedbackDTO.setRating(5);
        feedbackDTO.setActionItems("Continue to improve");
        feedbackDTO.setFeedbackDate(LocalDate.now());
        feedbackDTO.setStatus("PUBLISHED");
    }

    @Test
    @DisplayName("Given valid feedback DTO when provideFeedback is called then return the saved feedback DTO")
    public void givenValidFeedbackDTO_whenProvideFeedback_thenReturnSavedFeedbackDTO() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(employee));

        Employee manager = new Employee();
        manager.setId(2L);
        manager.setFirstName("Jane");
        manager.setLastName("Smith");
        when(employeeRepository.findById(2L)).thenReturn(java.util.Optional.of(manager));

        ManagerFeedback feedback = new ManagerFeedback();
        feedback.setEmployee(employee);
        feedback.setProvidedBy(manager);
        feedback.setFeedbackType("Performance");
        feedback.setFeedbackCategory("Performance Review");
        feedback.setFeedbackText("Great job!");
        feedback.setRating(5);
        feedback.setActionItems("Continue to improve");
        feedback.setFeedbackDate(LocalDate.now());
        feedback.setStatus("PUBLISHED");

        when(feedbackRepository.save(feedback)).thenReturn(feedback);

        // Act
        ManagerFeedbackDTO result = managerFeedbackService.provideFeedback(feedbackDTO);

        // Assert
        assertNotNull(result);
        assertEquals(feedbackDTO.getId(), result.getId());
        assertEquals(feedbackDTO.getEmployeeId(), result.getEmployeeId());
        assertEquals("John Doe", result.getEmployeeName());
        assertEquals(feedbackDTO.getProvidedById(), result.getProvidedById());
        assertEquals("Jane Smith", result.getProvidedByName());
        assertEquals(feedbackDTO.getFeedbackType(), result.getFeedbackType());
        assertEquals(feedbackDTO.getFeedbackCategory(), result.getFeedbackCategory());
        assertEquals(feedbackDTO.getFeedbackText(), result.getFeedbackText());
        assertEquals(feedbackDTO.getRating(), result.getRating());
        assertEquals(feedbackDTO.getActionItems(), result.getActionItems());
        assertEquals(feedbackDTO.getFeedbackDate(), result.getFeedbackDate());
        assertEquals(feedbackDTO.getStatus(), result.getStatus());
        assertEquals(feedback.getCreatedAt(), result.getCreatedAt());
        assertEquals(feedback.getUpdatedAt(), result.getUpdatedAt());

        // Verify repository interactions
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).findById(2L);
        verify(feedbackRepository, times(1)).save(feedback