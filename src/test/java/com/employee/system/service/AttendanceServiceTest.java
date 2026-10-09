package com.employee.system.service;

import com.employee.system.dto.AttendanceDTO;
import com.employee.system.entity.Attendance;
import com.employee.system.entity.Employee;
import com.employee.system.repository.AttendanceRepository;
import com.employee.system.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class AttendanceServiceTest {

    @InjectMocks
    private AttendanceService attendanceService;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
    }

    @Test
    @DisplayName("Given existing user, when markAttendance is called, then attendance is saved and returned as DTO")
    public void givenExistingUser_whenMarkAttendance_thenReturnAttendanceDTO() {
        // Arrange
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setEmployeeId(employee.getId());
        attendanceDTO.setAttendanceDate(LocalDate.now());
        attendanceDTO.setStatus("PRESENT");
        attendanceDTO.setCheckInTime("09:00");
        attendanceDTO.setCheckOutTime("17:00");
        attendanceDTO.setRemarks("None");

        Attendance expectedAttendance = new Attendance();
        expectedAttendance.setEmployee(employee);
        expectedAttendance.setAttendanceDate(attendanceDTO.getAttendanceDate());
        expectedAttendance.setStatus(attendanceDTO.getStatus());
        expectedAttendance.setCheckInTime(attendanceDTO.getCheckIn());
        expectedAttendance.setCheckOutTime(attendanceDTO.getCheckOutTime());
        expectedAttendance.setRemarks(attendanceDTO.getRemarks());

        when(employeeRepository.findById(employee.getId())).thenReturn(java.util.Optional.of(employee));
        when(attendanceRepository.save(expectedAttendance)).thenReturn(expectedAttendance);

        // Act
        AttendanceDTO result = attendanceService.markAttendance(attendanceDTO);

        // Assert
        assertEquals(expectedAttendance.getId(), result.getId());
        assertEquals(employee.getId(), result.getEmployeeId());
        assertEquals(employee.getFirstName() + " " + employee.getLastName(), result.getEmployeeName());
        assertEquals(attendanceDTO.getAttendanceDate(), result.getAttendanceDate());
        assertEquals(attendanceDTO.getStatus(), result.getStatus());
        assertEquals(attendanceDTO.getCheckInTime(), result.getCheckInTime());
        assertEquals(attendanceDTO.getCheckOutTime(), result.getCheckOutTime());
        assertEquals(attendanceDTO.getRemarks(), result.getRemarks());
        assertEquals(expectedAttendance.getCreatedAt(), result.getCreatedAt());
        assertEquals(expectedAttendance.getUpdatedAt(), result.getUpdatedAt());

        verify(employeeRepository, times(1)).findById(employee.getId());
        verify(attendanceRepository, times(1)).save(expectedAttendance);
    }

    // Add more test cases for other methods...
}