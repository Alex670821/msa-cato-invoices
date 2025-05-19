package com.cato.msa.invoices.service.impl;

import com.cato.msa.invoices.domain.InvoiceHeader;
import com.cato.msa.invoices.exceptions.NotFoundException;
import com.cato.msa.invoices.repository.InvoiceHeaderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceHeaderServiceImplGetInvoiceByNumberTest {

    @Mock
    private InvoiceHeaderRepository invoiceHeaderRepository;

    @InjectMocks
    private InvoiceHeaderServiceImpl invoiceHeaderService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void givenInvoiceExists_whenGetInvoiceByNumber_thenReturnInvoice() {
        String invoiceNumber = "INV001";
        InvoiceHeader invoice = new InvoiceHeader();
        invoice.setNumber(invoiceNumber);

        when(invoiceHeaderRepository.findByNumber(invoiceNumber)).thenReturn(Optional.of(invoice));

        InvoiceHeader result = invoiceHeaderService.getInvoiceByNumber(invoiceNumber);

        assertNotNull(result);
        assertEquals(invoiceNumber, result.getNumber());
        verify(invoiceHeaderRepository).findByNumber(invoiceNumber);
    }

    @Test
    void givenInvoiceDoesNotExist_whenGetInvoiceByNumber_thenThrowNotFoundException() {
        String invoiceNumber = "INV999";
        when(invoiceHeaderRepository.findByNumber(invoiceNumber)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> invoiceHeaderService.getInvoiceByNumber(invoiceNumber));
        verify(invoiceHeaderRepository).findByNumber(invoiceNumber);
    }
}