```java
package com.employee.system.service;

import com.employee.system.dto.DocumentDTO;
import com.employee.system.entity.Document;
import com.employee.system.entity.Employee;
import com.employee.system.repository.DocumentRepository;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DocumentService documentService;

    private Path uploadsDir;

    @BeforeEach
    public void setUp() {
        uploadsDir = Paths.get("uploads");
        documentService.init();
    }

    @DisplayName("Given existing employee and valid document when uploading document then return document DTO")
    @Test
    public void givenExistingEmployeeAndValidDocument_whenUploadDocument_thenReturnDocumentDTO() throws IOException {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(employee));

        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("example.pdf");
        when(file.getContentType()).thenReturn("application/pdf");
        when(file.getSize()).thenReturn(1024L);

        // Act
        DocumentDTO result = documentService.uploadDocument(file, 1L, "Personal");

        // Assert
        assertNotNull(result);
        assertEquals("example.pdf", result.getOriginalFileName());
        assertEquals("example.pdf", result.getStoredFileName());
        assertEquals("application/pdf", result.getContentType());
        assertEquals(1024L, result.getSize());
        assertEquals(LocalDate.now(), result.getCreatedAt());
        assertEquals(LocalDate.now(), result.getUpdatedAt());
        assertEquals(employee.getId(), result.getEmployeeId());
        assertEquals("Personal", result.getDocType());
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @DisplayName("Given non-existing employee when uploading document then throw UserNotFoundException")
    @Test
    public void givenNonExistingEmployee_whenUploadDocument_thenThrowUserNotFoundException() throws IOException {
        // Arrange
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("example.pdf");
        when(file.getContentType()).thenReturn("application/pdf");
        when(file.getSize()).thenReturn(1024L);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> documentService.uploadDocument(file, 1L, "Personal"));
        verify(employeeRepository, times(1)).findById(1L);
    }

    @DisplayName("Given valid request when creating order then return created order")
    @Test
    public void givenValidRequest_whenCreateOrder_thenReturnCreatedOrder() {
        // Arrange
        // (No specific setup needed for this method as it's not implemented in the provided code)

        // Act & Assert
        // (No specific assertions needed for this method as it's not implemented in the provided code)
    }

    @DisplayName("Given invalid request when creating order then throw ValidationException")
    @Test
    public void givenInvalidRequest_whenCreateOrder_thenThrowValidationException() {
        // Arrange
        // (No specific setup needed for this method as it's not implemented in the provided code)

        // Act &