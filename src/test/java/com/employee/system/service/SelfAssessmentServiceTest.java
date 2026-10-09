```java
package com.employee.system.service;

import com.employee.system.dto.SelfAssessmentDTO;
import com.employee.system.entity.Employee;
import com.employee.system.entity.SelfAssessment;
import com.employee.system.repository.EmployeeRepository;
import com.employee.system.repository.SelfAssessmentRepository;
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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class SelfAssessmentServiceTest {

    @InjectMocks
    private SelfAssessmentService selfAssessmentService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SelfAssessmentRepository assessmentRepository;

    private SelfAssessmentDTO assessmentDTO;
    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        assessmentDTO = new SelfAssessmentDTO();
        assessmentDTO.setEmployeeId(1L);
        assessmentDTO.setAssessmentPeriod("Q1 2023");
        assessmentDTO.setSelfRating(4);
        assessmentDTO.setTechnicalSkillsRating(5);
        assessmentDTO.setBehavioralRating(4);
        assessmentDTO.setLeadershipRating(5);
        assessmentDTO.setTeamworkRating(4);
        assessmentDTO.setAccomplishments("Completed project on time.");
        assessmentDTO.setStrengthsIdentified("Strong communication skills.");
        assessmentDTO.setImprovementAreas("Need to improve time management.");
        assessmentDTO.setCareerGoals("Promotion to Manager.");
        assessmentDTO.setSupportRequired("No support required.");
        assessmentDTO.setStatus("DRAFT");
    }

    @Test
    @DisplayName("Given existing employee and valid assessment DTO, when createAssessment is called, then the assessment is created and returned as a DTO")
    public void givenExistingEmployeeAndValidAssessmentDTO_whenCreateAssessmentIsCalled_thenAssessmentIsCreatedAndReturnedAsDTO() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(employee));
        when(assessmentRepository.save(any(SelfAssessment.class))).thenReturn(new SelfAssessment());

        // Act
        SelfAssessmentDTO result = selfAssessmentService.createAssessment(assessmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getEmployeeName());
        assertEquals("Q1 2023", result.getAssessmentPeriod());
        assertEquals(4, result.getSelfRating());
        assertEquals(5, result.getTechnicalSkillsRating());
        assertEquals(4, result.getBehavioralRating());
        assertEquals(5, result.getLeadershipRating());
        assertEquals(4, result.getTeamworkRating());
        assertEquals("Completed project on time.", result.getAccomplishments());
        assertEquals("Strong communication skills.", result.getStrengthsIdentified());
        assertEquals("Need to improve time management.", result.getImprovementAreas());
        assertEquals("Promotion to Manager.", result.getCareerGoals());
        assertEquals("No support required.", result.getSupportRequired());
        assertEquals("DRAFT", result.getStatus());
        assertEquals(LocalDate.now(), result.getAssessmentDate());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        verify(employeeRepository, times(1)).findById(1L);
        verify(assessmentRepository, times(1)).save(any(SelfAssessment.class));
    }

    @Test
    @DisplayName("Given existing employee and valid assessment DTO with null