package com.cato.msa.invoices.controller.rest;

import com.cato.msa.invoices.exceptions.NotFoundException;
import com.cato.msa.invoices.service.InvoiceHeaderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceHeaderControllerDeleteByIdTest {

    @Mock
    private InvoiceHeaderService invoiceHeaderService;

    @InjectMocks
    private InvoiceHeaderController invoiceHeaderController;

    @Test
    void givenInvoiceExists_whenDeleteById_thenReturnNoContent() {
        Long id = 1L;

        ResponseEntity<Void> response = invoiceHeaderController.deleteById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(invoiceHeaderService).deleteById(id);
    }

    @Test
    void givenInvoiceDoesNotExist_whenDeleteById_thenThrowNotFoundException() {
        Long id = 2L;
        doThrow(new NotFoundException("Invoice with id '" + id + "' was not found"))
                .when(invoiceHeaderService).deleteById(id);

        assertThrows(NotFoundException.class, () -> invoiceHeaderController.deleteById(id));
        verify(invoiceHeaderService).deleteById(id);
    }
}