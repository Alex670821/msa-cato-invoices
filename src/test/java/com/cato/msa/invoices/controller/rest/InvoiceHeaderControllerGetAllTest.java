package com.cato.msa.invoices.controller.rest;

import com.cato.msa.invoices.domain.InvoiceHeader;
import com.cato.msa.invoices.exceptions.NotContentException;
import com.cato.msa.invoices.service.InvoiceHeaderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvoiceHeaderControllerGetAllTest {
    @Mock
    private InvoiceHeaderService invoiceHeaderService;

    @InjectMocks
    private InvoiceHeaderController invoiceHeaderController;

    @Test
    void givenInvoicesExist_whenFindAll_thenReturnResponseEntityWithList() {
        InvoiceHeader invoice1 = new InvoiceHeader();
        InvoiceHeader invoice2 = new InvoiceHeader();
        List<InvoiceHeader> invoices = Arrays.asList(invoice1, invoice2);

        when(invoiceHeaderService.getAll()).thenReturn(invoices);

        ResponseEntity<List<InvoiceHeader>> response = invoiceHeaderController.findAll();

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldThrowNotContentException_whenNoInvoicesExist() {
        when(invoiceHeaderService.getAll()).thenThrow(new NotContentException("Not Content"));

        assertThrows(NotContentException.class, () -> invoiceHeaderController.findAll());
    }
}