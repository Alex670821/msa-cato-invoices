package com.cato.msa.invoices.controller.rest;

import com.cato.msa.invoices.domain.InvoiceHeader;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvoiceHeaderControllerGetInvoiceByNumberTest {

    @Mock
    private InvoiceHeaderService invoiceHeaderService;

    @InjectMocks
    private InvoiceHeaderController invoiceHeaderController;

    @Test
    void givenInvoiceExists_whenFindByNumber_thenReturnInvoiceAndStatusOk() {
        String invoiceNumber = "INV001";
        InvoiceHeader invoice = new InvoiceHeader();
        invoice.setNumber(invoiceNumber);

        when(invoiceHeaderService.getInvoiceByNumber(invoiceNumber)).thenReturn(invoice);

        ResponseEntity<InvoiceHeader> response = invoiceHeaderController.findByNumber(invoiceNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(invoiceNumber, response.getBody().getNumber());
    }

    @Test
    void givenInvoiceDoesNotExist_whenFindByNumber_thenThrowNotFoundException() {
        String invoiceNumber = "INV999";
        when(invoiceHeaderService.getInvoiceByNumber(invoiceNumber))
                .thenThrow(new NotFoundException("Invoice with number '" + invoiceNumber + "' was not found"));

        assertThrows(NotFoundException.class, () -> invoiceHeaderController.findByNumber(invoiceNumber));
    }
}