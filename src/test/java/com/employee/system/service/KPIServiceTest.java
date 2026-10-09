```java
package com.employee.system.service;

import com.employee.system.dto.KPIDTO;
import com.employee.system.entity.Employee;
import com.employee.system.entity.KPI;
import com.employee.system.repository.EmployeeRepository;
import com.employee.system.repository.KPIRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KPIServiceTest {

    @Mock
    private KPIRepository kpiRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private KPIService kpiService;

    private Employee employee;
    private KPI kpi;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        kpi = new KPI();
        kpi.setId(1L);
        kpi.setEmployee(employee);
        kpi.setKpiName("Example KPI");
        kpi.setKpiDescription("This is an example KPI.");
        kpi.setMeasurementUnit("Units");
        kpi.setTargetValue(BigDecimal.valueOf(100));
        kpi.setCurrentValue(BigDecimal.valueOf(50));
        kpi.setWeight(BigDecimal.valueOf(0.5));
        kpi.setFrequency("Monthly");
        kpi.setStartDate(LocalDate.now());
        kpi.setEndDate(LocalDate.now().plusMonths(1));
        kpi.setStatus("ACTIVE");
        kpi.setRemarks("This is a test KPI.");
    }

    @Test
    @DisplayName("Given existing employee and valid KPI DTO, when createKPI is called, then KPI is created and returned as DTO")
    public void givenExistingEmployeeAndValidKPIDTO_whenCreateKPIIsCalled_thenKPIIsCreatedAndReturnedAsDTO() {
        // Arrange
        KPIDTO kpiDTO = new KPIDTO();
        kpiDTO.setEmployeeId(1L);
        kpiDTO.setKpiName("Example KPI");
        kpiDTO.setKpiDescription("This is an example KPI.");
        kpiDTO.setMeasurementUnit("Units");
        kpiDTO.setTargetValue(BigDecimal.valueOf(100));
        kpiDTO.setCurrentValue(BigDecimal.valueOf(50));
        kpiDTO.setWeight(BigDecimal.valueOf(0.5));
        kpiDTO.setFrequency("Monthly");
        kpiDTO.setStartDate(LocalDate.now());
        kpiDTO.setEndDate(LocalDate.now().plusMonths(1));
        kpiDTO.setStatus("ACTIVE");
        kpiDTO.setRemarks("This is a test KPI.");

        // Act
        KPIDTO result = kpiService.createKPI(kpiDTO);

        // Assert
        verify(kpiRepository, times(1)).save(any(KPI.class));
        assertEquals(kpiDTO.getKpiName(), result.getKpiName());
        assertEquals(kpiDTO.getKpiDescription(), result.getKpiDescription());
        assertEquals(kpiDTO.getMeasurementUnit(), result.getMeasurementUnit());
        assertEquals(kpiDTO.getTargetValue(), result.getTargetValue());
        assertEquals(kpiDTO.getCurrentValue(), result.getCurrentValue());
        assertEquals(kpiDTO.getWeight(), result.getWeight());
        assertEquals(kpiDTO.getFrequency(), result.getFrequency());
        assertEquals(kpiDTO.getStartDate(), result.getStartDate());
        assertEquals(kpiDTO.getEndDate(), result.getEndDate());
        assertEquals(kpiDTO.getStatus(), result