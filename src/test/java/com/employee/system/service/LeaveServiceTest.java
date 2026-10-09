```java
package com.employee.system.service;

import com.employee.system.dto.LeaveBalanceDTO;
import com.employee.system.dto.LeaveDTO;
import com.employee.system.entity.Employee;
import com.employee.system.entity.Holiday;
import com.employee.system.entity.Leave;
import com.employee.system.repository.EmployeeRepository;
import com.employee.system.repository.HolidayRepository;
import com.employee.system.repository.LeaveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LeaveServiceTest {

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private HolidayRepository holidayRepository;

    @InjectMocks
    private LeaveService leaveService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(leaveService, "defaultEntitlements", new HashMap<String, Integer>() {{
            put("SICK", 10);
            put("CASUAL", 7);
            put("EARNED", 15);
        }});
    }

    @DisplayName("Given existing employee and valid leave details, when applyLeave is called, then leave is applied successfully")
    @Test
    public void givenExistingEmployeeAndValidLeaveDetails_whenApplyLeave_isCalled_thenLeaveIsAppliedSuccessfully() {
        // Arrange
        Long employeeId = 1L;
        LeaveDTO dto = new LeaveDTO();
        dto.setEmployeeId(employeeId);
        dto.setStartDate(LocalDate.of(2023, 1, 1));
        dto.setEndDate(LocalDate.of(2023, 1, 5));
        dto.setLeaveType("SICK");
        dto.setReason("Need to attend a medical appointment");

        Employee employee = new Employee();
        employee.setId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        when(holidayRepository.findBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(List.of());

        // Act
        LeaveDTO result = leaveService.applyLeave(dto);

        // Assert
        verify(leaveRepository, times(1)).save(any(Leave.class));
        assertEquals(employeeId, result.getEmployeeId());
        assertEquals("SICK", result.getLeaveType());
        assertEquals("PENDING", result.getStatus());
        assertEquals(5, result.getDays());
    }

    @DisplayName("Given invalid leave date range, when applyLeave is called, then an exception is thrown")
    @Test
    public void givenInvalidLeaveDateRange_whenApplyLeave_isCalled_thenAnExceptionIsThrown() {
        // Arrange
        LeaveDTO dto = new LeaveDTO();
        dto.setStartDate(LocalDate.of(2023, 1, 5));
        dto.setEndDate(LocalDate.of(2023, 1, 1));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> leaveService.applyLeave(dto));
    }

    @DisplayName("Given existing employee and leave conflict, when applyLeave is called, then an exception is thrown")
    @Test
    public void givenExistingEmployeeAndLeaveConflict_whenApplyLeave_isCalled_thenAnExceptionIsThrown() {
        // Arrange
        Long employeeId = 1L;
        LeaveDTO dto = new LeaveDTO();
        dto.setEmployeeId(employeeId);
        dto.setStartDate(LocalDate.of(2023, 1, 1));
        dto.setEndDate(LocalDate.of(2023, 1, 5));
        dto.setLeaveType