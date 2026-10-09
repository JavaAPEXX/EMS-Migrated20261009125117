```java
package com.employee.system.service;

import com.employee.system.dto.PerformanceReviewDTO;
import com.employee.system.entity.Employee;
import com.employee.system.entity.PerformanceReview;
import com.employee.system.repository.EmployeeRepository;
import com.employee.system.repository.PerformanceReviewRepository;
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
public class PerformanceReviewServiceTest {

    @InjectMocks
    private PerformanceReviewService performanceReviewService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PerformanceReviewRepository reviewRepository;

    private Employee employee;
    private Employee manager;
    private PerformanceReview review;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        manager = new Employee();
        manager.setId(2L);
        manager.setFirstName("Jane");
        manager.setLastName("Smith");

        review = new PerformanceReview();
        review.setEmployee(employee);
        review.setReviewedBy(manager);
        review.setReviewPeriod("Q1 2023");
        review.setReviewDate(LocalDate.now());
        review.setOverallRating(4);
        review.setTechnicalSkillsRating(5);
        review.setBehavioralRating(4);
        review.setLeadershipRating(5);
        review.setTeamworkRating(4);
        review.setStrengths("Good communication skills");
        review.setAreasForImprovement("Need to improve time management");
        review.setComments("Overall, a good performance review.");
        review.setStatus("APPROVED");
    }

    @Test
    @DisplayName("Given existing employee and manager, when createReview, then return created review")
    public void givenExistingEmployeeAndManager_whenCreateReview_thenReturnCreatedReview() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(manager));
        when(reviewRepository.save(review)).thenReturn(review);

        // Act
        PerformanceReviewDTO createdReviewDTO = performanceReviewService.createReview(convertToDTO(review));

        // Assert
        assertNotNull(createdReviewDTO);
        assertEquals(review.getId(), createdReviewDTO.getId());
        assertEquals(employee.getId(), createdReviewDTO.getEmployeeId());
        assertEquals(manager.getId(), createdReviewDTO.getReviewedById());
        assertEquals(review.getReviewPeriod(), createdReviewDTO.getReviewPeriod());
        assertEquals(review.getReviewDate(), createdReviewDTO.getReviewDate());
        assertEquals(review.getOverallRating(), createdReviewDTO.getOverallRating());
        assertEquals(review.getTechnicalSkillsRating(), createdReviewDTO.getTechnicalSkillsRating());
        assertEquals(review.getBehavioralRating(), createdReviewDTO.getBehavioralRating());
        assertEquals(review.getLeadershipRating(), createdReviewDTO.getLeadershipRating());
        assertEquals(review.getTeamworkRating(), createdReviewDTO.getTeamworkRating());
        assertEquals(review.getStrengths(), createdReviewDTO.getStrengths());
        assertEquals(review.getAreasForImprovement(), createdReviewDTO.getAreasForImprovement());
        assertEquals(review.getComments(), createdReviewDTO.getComments());
        assertEquals(review.getStatus(), createdReviewDTO.getStatus());
        assertEquals(review.getCreatedAt(), createdReviewDTO.getCreatedAt());
        assertEquals(review.getUpdatedAt(), createdReviewDTO.getUpdatedAt());
    }

    @Test
    @DisplayName("Given reviewId, when getReviewById, then return reviewDTO")
    public void givenReviewId_whenGetReviewById_thenReturnReviewDTO() {
        // Arrange
        when(reviewRepository.findById(1L)).