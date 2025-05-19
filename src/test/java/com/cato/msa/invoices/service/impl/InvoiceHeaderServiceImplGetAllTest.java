package com.cato.msa.invoices.service.impl;

import com.cato.msa.invoices.domain.InvoiceHeader;
import com.cato.msa.invoices.exceptions.NotContentException;
import com.cato.msa.invoices.repository.InvoiceHeaderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvoiceHeaderServiceImplGetAllTest {

    @InjectMocks
    private InvoiceHeaderServiceImpl invoiceHeaderService;

    @Mock
    private InvoiceHeaderRepository invoiceHeaderRepository;


    @Test
    void givenInvoicesExist_whenGetAll_thenReturnInvoiceList() {
        // given
        InvoiceHeader invoice1 = new InvoiceHeader();
        InvoiceHeader invoice2 = new InvoiceHeader();
        List<InvoiceHeader> invoices = Arrays.asList(invoice1, invoice2);

        // when
        when(invoiceHeaderRepository.findAll()).thenReturn(invoices);

        // then
        List<InvoiceHeader> result = invoiceHeaderService.getAll();

        // assert
        assertEquals(2, result.size());
    }

    @Test
    void shouldThrowNotContentException_whenNoInvoicesExist() {
        // should
        when(invoiceHeaderRepository.findAll()).thenReturn(Collections.emptyList());

        // then & assert
        assertThrows(NotContentException.class, () -> invoiceHeaderService.getAll());
    }
}
