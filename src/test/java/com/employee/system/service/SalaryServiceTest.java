```java
package com.employee.system.service;

import com.employee.system.dto.SalaryDTO;
import com.employee.system.entity.Employee;
import com.employee.system.entity.Salary;
import com.employee.system.repository.EmployeeRepository;
import com.employee.system.repository.SalaryRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SalaryServiceTest {

    @InjectMocks
    private SalaryService salaryService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SalaryRepository salaryRepository;

    private SalaryDTO salaryDTO;
    private Salary salary;

    @BeforeEach
    public void setUp() {
        salaryDTO = new SalaryDTO();
        salaryDTO.setEmployeeId(1L);
        salaryDTO.setSalaryMonth(1);
        salaryDTO.setSalaryYear(2023);
        salaryDTO.setBaseSalary(new BigDecimal("5000"));
        salaryDTO.setDearnessAllowance(new BigDecimal("500"));
        salaryDTO.setHouseRentAllowance(new BigDecimal("300"));
        salaryDTO.setOtherAllowances(new BigDecimal("200"));
        salaryDTO.setIncomeTax(new BigDecimal("100"));
        salaryDTO.setProvidentFund(new BigDecimal("200"));
        salaryDTO.setOtherDeductions(new BigDecimal("50"));
        salaryDTO.setPaymentStatus("PENDING");
        salaryDTO.setPaymentDate(LocalDate.now());
        salaryDTO.setRemarks("Test Salary");

        salary = new Salary();
        salary.setId(1L);
        salary.setEmployee(new Employee(1L, "John", "Doe"));
        salary.setSalaryMonth(1);
        salary.setSalaryYear(2023);
        salary.setBaseSalary(new BigDecimal("5000"));
        salary.setDearnessAllowance(new BigDecimal("500"));
        salary.setHouseRentAllowance(new BigDecimal("300"));
        salary.setOtherAllowances(new BigDecimal("200"));
        salary.setTotalAllowances(new BigDecimal("1000"));
        salary.setIncomeTax(new BigDecimal("100"));
        salary.setProvidentFund(new BigDecimal("200"));
        salary.setOtherDeductions(new BigDecimal("50"));
        salary.setNetSalary(new BigDecimal("3550"));
        salary.setPaymentStatus("PENDING");
        salary.setPaymentDate(LocalDate.now());
        salary.setRemarks("Test Salary");
    }

    @Test
    @DisplayName("Given valid salary DTO, when addSalary is called, then return saved salary DTO")
    public void givenValidSalaryDTO_whenAddSalary_isCalled_thenReturnSavedSalaryDTO() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee(1L, "John", "Doe")));
        when(salaryRepository.save(any(Salary.class))).thenReturn(salary);

        // Act
        SalaryDTO result = salaryService.addSalary(salaryDTO);

        // Assert
        assertNotNull(result);
        assertEquals(salaryDTO.getEmployeeId(), result.getEmployeeId());
        assertEquals(salaryDTO.getSalaryMonth(), result.getSalaryMonth());
        assertEquals(salaryDTO.getSalaryYear(), result.getSalaryYear());
        assertEquals(salaryDTO.getBaseSalary(), result.getBaseSalary());
        assertEquals(salaryDTO.getDearnessAllowance(), result.getDearnessAllowance());
        assertEquals(salaryDTO.getHouseRentAllowance(), result.getHouseRentAllowance());
        assertEquals(salaryDTO.getOtherAllowances(), result.get