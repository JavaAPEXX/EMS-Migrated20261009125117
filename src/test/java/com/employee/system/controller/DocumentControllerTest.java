```java
package com.employee.system.controller;

import com.employee.system.dto.DocumentDTO;
import com.employee.system.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Given valid document upload, when upload is successful, then return created document DTO")
    @Test
    public void givenValidDocumentUpload_whenUploadIsSuccessful_thenReturnCreatedDocumentDTO() throws IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        Long employeeId = 1L;
        String docType = "testDoc";
        DocumentDTO expectedDto = new DocumentDTO();
        when(documentService.uploadDocument(file, employeeId, docType)).thenReturn(expectedDto);

        // Act
        ResponseEntity<DocumentDTO> response = documentController.upload(file, employeeId, docType);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(expectedDto, response.getBody());
    }

    @DisplayName("Given existing employee ID, when list documents by employee is successful, then return list of document DTOs")
    @Test
    public void givenExistingEmployeeId_whenListDocumentsByEmployeeIsSuccessful_thenReturnListOfDocumentDTOs() {
        // Arrange
        Long employeeId = 1L;
        List<DocumentDTO> expectedDtos = List.of(new DocumentDTO());
        when(documentService.listByEmployee(employeeId)).thenReturn(expectedDtos);

        // Act
        ResponseEntity<List<DocumentDTO>> response = documentController.listByEmployee(employeeId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDtos, response.getBody());
    }

    @DisplayName("Given valid document ID, when download document is successful, then return document resource with correct headers")
    @Test
    public void givenValidDocumentId_whenDownloadDocumentIsSuccessful_thenReturnDocumentResourceWithCorrectHeaders() throws MalformedURLException {
        // Arrange
        Long id = 1L;
        Resource expectedResource = mock(Resource.class);
        when(documentService.downloadDocument(id)).thenReturn(expectedResource);

        // Act
        ResponseEntity<Resource> response = documentController.download(id);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResource, response.getBody());
        assertEquals("attachment; filename=\"document.pdf\"", response.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION).get(0));
    }

    @DisplayName("Given valid document ID, when delete document is successful, then return no content response")
    @Test
    public void givenValidDocumentId_whenDeleteDocumentIsSuccessful_thenReturnNoContentResponse() throws IOException {
        // Arrange
        Long id = 1L;
        doNothing().when(documentService).deleteDocument(id);

        // Act
        ResponseEntity<Void> response = documentController.delete(id);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }

    @DisplayName("Given non-existing document ID, when delete document fails, then return no content response")
    @Test