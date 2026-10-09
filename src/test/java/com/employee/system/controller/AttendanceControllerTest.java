```java
package com.employee.system.controller;

import com.employee.system.dto.AttendanceDTO;
import com.employee.system.dto.EmployeeDTO;
import com.employee.system.dto.AttendanceSummary;
import com.employee.system.service.AttendanceService;
import com.employee.system.service.EmployeeService;
import com.employee.system.service.AttendanceSummaryService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AttendanceControllerTest {

    @InjectMocks
    private AttendanceController attendanceController;

    @Mock
    private AttendanceService attendanceService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private AttendanceSummaryService attendanceSummaryService;

    private AttendanceDTO attendanceDTO;
    private EmployeeDTO employeeDTO;
    private AttendanceSummary attendanceSummary;

    @BeforeEach
    public void setUp() {
        attendanceDTO = new AttendanceDTO();
        attendanceDTO.setId(1L);
        attendanceDTO.setEmployeeId(1L);
        attendanceDTO.setDate(LocalDate.now());
        attendanceDTO.setStatus("Present");

        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");

        attendanceSummary = new AttendanceSummary();
        attendanceSummary.setEmployeeId(1L);
        attendanceSummary.setTotalPresent(10);
        attendanceSummary.setTotalAbsent(5);
        attendanceSummary.setTotalLate(3);
    }

    @Test
    @DisplayName("Given valid attendance data, when markAttendance is called, then createdAttendance is returned")
    public void givenValidAttendanceData_whenMarkAttendance_isCalled_thenCreatedAttendanceIsReturned() {
        // Arrange
        when(attendanceService.markAttendance(attendanceDTO)).thenReturn(attendanceDTO);

        // Act
        ResponseEntity<AttendanceDTO> response = attendanceController.markAttendance(attendanceDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(attendanceDTO, response.getBody());
        verify(attendanceService, times(1)).markAttendance(attendanceDTO);
    }

    @Test
    @DisplayName("Given existing attendance ID, when getAttendanceById is called, then attendance is returned")
    public void givenExistingAttendanceId_whenGetAttendanceById_isCalled_thenAttendanceIsReturned() {
        // Arrange
        when(attendanceService.getAttendanceById(1L)).thenReturn(attendanceDTO);

        // Act
        ResponseEntity<AttendanceDTO> response = attendanceController.getAttendanceById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(attendanceDTO, response.getBody());
        verify(attendanceService, times(1)).getAttendanceById(1L);
    }

    @Test
    @DisplayName("Given employee ID and date, when getAttendanceByEmployeeAndDate is called, then attendance is returned")
    public void givenEmployeeIdAndDate_whenGetAttendanceByEmployeeAndDate_isCalled_thenAttendanceIsReturned() {
        // Arrange
        when(attendanceService.getAttendanceByEmployeeAndDate(1L, LocalDate.now())).thenReturn(attendanceDTO);

        // Act
        ResponseEntity<AttendanceDTO> response = attendanceController.getAttendanceByEmployeeAndDate(1L, LocalDate.now());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(attendanceDTO, response.getBody());
        verify(attendanceService, times(1)).getAttendanceByEmployeeAndDate(1L, LocalDate.now());
    }

    @Test
    @DisplayName("Given employee ID, when getAttendanceByEmployee is called, then list of attendance is returned")
    public