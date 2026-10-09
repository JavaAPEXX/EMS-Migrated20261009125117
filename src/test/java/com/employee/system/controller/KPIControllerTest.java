```java
package com.employee.system.controller;

import com.employee.system.dto.KPIDTO;
import com.employee.system.service.KPIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KPIControllerTest {

    @InjectMocks
    private KPIController kpiController;

    @Mock
    private KPIService kpiService;

    private KPIDTO kpiDTO;
    private KPIDTO updatedKPI;
    private List<KPIDTO> kpis;
    private Long id;

    @BeforeEach
    public void setUp() {
        kpiDTO = new KPIDTO();
        kpiDTO.setId(1L);
        kpiDTO.setName("Test KPI");
        kpiDTO.setTargetValue(BigDecimal.valueOf(100));
        kpiDTO.setStartDate("2023-01-01");
        kpiDTO.setEndDate("2023-12-31");
        kpiDTO.setFrequency("Monthly");

        updatedKPI = new KPIDTO();
        updatedKPI.setId(1L);
        updatedKPI.setName("Updated Test KPI");
        updatedKPI.setTargetValue(BigDecimal.valueOf(150));
        updatedKPI.setStartDate("2023-01-01");
        updatedKPI.setEndDate("2023-12-31");
        updatedKPI.setFrequency("Monthly");

        kpis = List.of(kpiDTO);

        id = 1L;
    }

    @Test
    @DisplayName("Given a valid KPIDTO, when createKPI is called, then a new KPI is created and returned with CREATED status")
    public void givenValidKPIDTO_whenCreateKPI_isCalled_thenNewKPIIsCreatedAndReturnedWithCREATEDStatus() {
        // Arrange
        when(kpiService.createKPI(kpiDTO)).thenReturn(kpiDTO);

        // Act
        ResponseEntity<KPIDTO> response = kpiController.createKPI(kpiDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(kpiDTO, response.getBody());
        verify(kpiService, times(1)).createKPI(kpiDTO);
    }

    @Test
    @DisplayName("Given a valid KPIDTO, when createKPI is called, then a new KPI is created and returned with CREATED status (Mockito ArgumentCaptor)")
    public void givenValidKPIDTO_whenCreateKPI_isCalled_thenNewKPIIsCreatedAndReturnedWithCREATEDStatus_MockitoArgumentCaptor() {
        // Arrange
        ArgumentCaptor<KPIDTO> kpiCaptor = ArgumentCaptor.forClass(KPIDTO.class);
        when(kpiService.createKPI(kpiCaptor.capture())).thenReturn(kpiDTO);

        // Act
        ResponseEntity<KPIDTO> response = kpiController.createKPI(kpiDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(kpiDTO, response.getBody());
        verify(kpiService, times(1)).createKPI(kpiCaptor.getValue());
        assertEquals(kpiDTO, kpiCCaptor.getValue());
    }

    @Test
    @DisplayName("Given a valid KPIDTO, when createKPI is called, then a new KPI is created and returned with CREATED status (Mockito ArgumentCaptor