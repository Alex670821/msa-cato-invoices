package com.cato.msa.invoices.service.impl;

import com.cato.msa.invoices.exceptions.NotFoundException;
import com.cato.msa.invoices.repository.InvoiceHeaderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceHeaderServiceImplDeleteByIdTest {

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
    void givenInvoiceExists_whenDeleteById_thenRepositoryDeleteByIdCalled() {
        Long id = 1L;
        when(invoiceHeaderRepository.existsById(id)).thenReturn(true);

        invoiceHeaderService.deleteById(id);

        verify(invoiceHeaderRepository).existsById(id);
        verify(invoiceHeaderRepository).deleteById(id);
    }

    @Test
    void givenInvoiceDoesNotExist_whenDeleteById_thenThrowNotFoundException() {
        Long id = 2L;
        when(invoiceHeaderRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> invoiceHeaderService.deleteById(id));
        verify(invoiceHeaderRepository).existsById(id);
        verify(invoiceHeaderRepository, never()).deleteById(anyLong());
    }
}