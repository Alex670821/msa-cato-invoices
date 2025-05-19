package com.cato.msa.invoices.service.impl;

import com.cato.msa.invoices.constant.Constant;
import com.cato.msa.invoices.domain.InvoiceDetail;
import com.cato.msa.invoices.domain.InvoiceHeader;
import com.cato.msa.invoices.repository.InvoiceHeaderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class InvoiceHeaderServiceCreateTest {

    @InjectMocks
    private InvoiceHeaderServiceImpl invoiceHeaderService;

    @Mock
    private InvoiceHeaderRepository invoiceHeaderRepository;

    @Test
    void givenValidInvoiceHeader_whenCreateInvoiceHeader_thenReturnInvoiceHeaderWithCalculatedAmounts() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date invoiceDate = sdf.parse("2025-08-14T18:23:00");

        InvoiceDetail detalle = new InvoiceDetail();
        detalle.setProductName("Apple Watch");
        detalle.setQuantity(1);
        detalle.setUnitPrice(new BigDecimal("100.00"));

        InvoiceHeader expectedInvoiceHeader = new InvoiceHeader();
        expectedInvoiceHeader.setNumber("INV-12389");
        expectedInvoiceHeader.setCustomerName("Alex Juca");
        expectedInvoiceHeader.setDate(invoiceDate);
        expectedInvoiceHeader.setInvoiceDetails(List.of(detalle));

        Mockito.when(invoiceHeaderRepository.save(any())).thenReturn(expectedInvoiceHeader);

        InvoiceHeader response = invoiceHeaderService.createInvoiceHeader(expectedInvoiceHeader);

        assertEquals("INV-12389", response.getNumber());
        assertEquals("Alex Juca", response.getCustomerName());
        assertEquals(invoiceDate, response.getDate());
        assertEquals(0, response.getSubTotalAmount().compareTo(new BigDecimal("100.00")));
        assertEquals(0, response.getVatAmount().compareTo(new BigDecimal("100.00").multiply(Constant.VAT_RATE)));
        assertEquals(0, response.getTotalAmount().compareTo(new BigDecimal("100.00").add(new BigDecimal("100.00").multiply(Constant.VAT_RATE))));
        assertEquals(1, response.getInvoiceDetails().size());
        InvoiceDetail firstDetail = response.getInvoiceDetails().stream().findFirst().orElseThrow();
        assertEquals("Apple Watch", firstDetail.getProductName());
        assertEquals(0, firstDetail.getSubTotal().compareTo(new BigDecimal("100.00")));
        assertEquals(expectedInvoiceHeader, firstDetail.getInvoiceHeader());
    }

    @Test
    void shouldReturnInvoiceHeaderWithZeroAmounts_whenSendInvoiceHeaderWithNoDetails() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date invoiceDate = sdf.parse("2025-08-14T18:23:00");

        InvoiceHeader expectedInvoiceHeader = new InvoiceHeader();
        expectedInvoiceHeader.setNumber("INV-12389");
        expectedInvoiceHeader.setCustomerName("Alex Juca");
        expectedInvoiceHeader.setDate(invoiceDate);
        expectedInvoiceHeader.setInvoiceDetails(List.of());

        Mockito.when(invoiceHeaderRepository.save(any())).thenReturn(expectedInvoiceHeader);

        InvoiceHeader response = invoiceHeaderService.createInvoiceHeader(expectedInvoiceHeader);

        assertEquals("INV-12389", response.getNumber());
        assertEquals("Alex Juca", response.getCustomerName());
        assertEquals(invoiceDate, response.getDate());
        assertEquals(0, response.getSubTotalAmount().compareTo(BigDecimal.ZERO));
        assertEquals(0, response.getVatAmount().compareTo(BigDecimal.ZERO));
        assertEquals(0, response.getTotalAmount().compareTo(BigDecimal.ZERO));
        assertTrue(response.getInvoiceDetails().isEmpty());
    }
}