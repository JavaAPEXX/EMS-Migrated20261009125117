```java
package com.employee.system.service;

import com.employee.system.dto.RatingScaleDTO;
import com.employee.system.entity.RatingScale;
import com.employee.system.repository.RatingScaleRepository;
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
public class RatingScaleServiceTest {

    @Mock
    private RatingScaleRepository ratingScaleRepository;

    @InjectMocks
    private RatingScaleService ratingScaleService;

    private RatingScaleDTO ratingScaleDTO;
    private RatingScale ratingScale;

    @BeforeEach
    public void setUp() {
        ratingScaleDTO = new RatingScaleDTO();
        ratingScaleDTO.setId(1L);
        ratingScaleDTO.setRatingValue(5);
        ratingScaleDTO.setRatingLabel("Excellent");
        ratingScaleDTO.setRatingDescription("Outstanding performance");
        ratingScaleDTO.setColorCode("#00FF00");
        ratingScaleDTO.setMinimumScore(0);
        ratingScaleDTO.setMaximumScore(100);
        ratingScaleDTO.setIsActive(true);

        ratingScale = new RatingScale();
        ratingScale.setId(1L);
        ratingScale.setRatingValue(5);
        ratingScale.setRatingLabel("Excellent");
        ratingScale.setRatingDescription("Outstanding performance");
        ratingScale.setColorCode("#00FF00");
        ratingScale.setMinimumScore(0);
        ratingScale.setMaximumScore(100);
        ratingScale.setIsActive(true);
    }

    @Test
    @DisplayName("Given existing rating scale ID, when getRatingScaleById, then return RatingScaleDTO")
    public void givenExistingRatingScaleId_whenGetRatingScaleById_thenReturnRatingScaleDTO() {
        // Arrange
        when(ratingScaleRepository.findById(1L)).thenReturn(java.util.Optional.of(ratingScale));

        // Act
        RatingScaleDTO result = ratingScaleService.getRatingScaleById(1L);

        // Assert
        assertEquals(ratingScaleDTO, result);
        verify(ratingScaleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Given non-existing rating scale ID, when getRatingScaleById, then throw UserNotFoundException")
    public void givenNonExistingRatingScaleId_whenGetRatingScaleById_thenThrowUserNotFoundException() {
        // Arrange
        when(ratingScaleRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> ratingScaleService.getRatingScaleById(1L));
        verify(ratingScaleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Given rating scale value, when getRatingScaleByValue, then return RatingScaleDTO")
    public void givenRatingScaleValue_whenGetRatingScaleByValue_thenReturnRatingScaleDTO() {
        // Arrange
        when(ratingScaleRepository.findByRatingValue(5)).thenReturn(java.util.Optional.of(ratingScale));

        // Act
        RatingScaleDTO result = ratingScaleService.getRatingScaleByValue(5);

        // Assert
        assertEquals(ratingScaleDTO, result);
        verify(ratingScaleRepository, times(1)).findByRatingValue(5);
    }

    @Test
    @DisplayName("Given non-existing rating scale value, when getRatingScaleByValue, then throw UserNotFoundException")
    public void givenNonExistingRatingScaleValue_whenGetRatingScaleByValue_thenThrowUserNotFoundException() {
        // Arrange
        when(ratingScaleRepository.findByRatingValue(5)).thenReturn(java.util.Optional.empty());

        // Act &