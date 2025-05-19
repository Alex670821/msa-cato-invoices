package com.cato.msa.invoices.controller.rest;

import com.cato.msa.invoices.constant.Constant;
import com.cato.msa.invoices.domain.InvoiceDetail;
import com.cato.msa.invoices.domain.InvoiceHeader;
import com.cato.msa.invoices.service.InvoiceHeaderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class InvoiceHeaderControllerCreateTest {

    @Mock
    private InvoiceHeaderService invoiceHeaderService;

    @InjectMocks
    private InvoiceHeaderController invoiceHeaderController;

    @Test
    void shouldReturnInvoiceHeader_whenSendCorrectInvoiceHeader() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date invoiceDate = sdf.parse("2025-08-14T18:23:00");

        InvoiceDetail detail = new InvoiceDetail();
        detail.setProductName("Apple Watch");
        detail.setQuantity(1);
        detail.setUnitPrice(new BigDecimal("100.00"));
        detail.calculateSubTotal();

        InvoiceHeader expectedInvoiceHeader = new InvoiceHeader();
        expectedInvoiceHeader.setNumber("INV-12389");
        expectedInvoiceHeader.setCustomerName("Alex Juca");
        expectedInvoiceHeader.setDate(invoiceDate);
        expectedInvoiceHeader.setInvoiceDetails(List.of(detail));
        expectedInvoiceHeader.calculateInvoiceAmounts();

        Mockito.when(invoiceHeaderService.createInvoiceHeader(any())).thenReturn(expectedInvoiceHeader);

        ResponseEntity<InvoiceHeader> response = invoiceHeaderController.save(expectedInvoiceHeader);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INV-12389", response.getBody().getNumber());
        assertEquals("Alex Juca", response.getBody().getCustomerName());
        assertEquals(invoiceDate, response.getBody().getDate());
        assertEquals(0, new BigDecimal("100.00").compareTo(response.getBody().getSubTotalAmount()));
        assertEquals(0, new BigDecimal("100.00").multiply(Constant.VAT_RATE).compareTo(response.getBody().getVatAmount()));
        assertEquals(0, new BigDecimal("100.00").add(new BigDecimal("100.00").multiply(Constant.VAT_RATE)).compareTo(response.getBody().getTotalAmount()));
        assertNotNull(response.getBody().getInvoiceDetails());
        assertEquals(1, response.getBody().getInvoiceDetails().size());
        InvoiceDetail firstDetail = response.getBody().getInvoiceDetails().stream().findFirst().orElseThrow();
        assertEquals("Apple Watch", firstDetail.getProductName());
        assertEquals(1, firstDetail.getQuantity());
        assertEquals(0, new BigDecimal("100.00").compareTo(firstDetail.getUnitPrice()));
        assertEquals(0, new BigDecimal("100.00").compareTo(firstDetail.getSubTotal()));
        assertEquals(expectedInvoiceHeader, firstDetail.getInvoiceHeader());
    }

    @Test
    void shouldReturnInvoiceHeaderWithZeroAmounts_whenSendInvoiceHeaderWithEmptyDetails() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date invoiceDate = sdf.parse("2025-08-14T18:23:00");

        InvoiceHeader expectedInvoiceHeader = new InvoiceHeader();
        expectedInvoiceHeader.setNumber("INV-12389");
        expectedInvoiceHeader.setCustomerName("Alex Juca");
        expectedInvoiceHeader.setDate(invoiceDate);
        expectedInvoiceHeader.setInvoiceDetails(List.of());
        expectedInvoiceHeader.calculateInvoiceAmounts();

        Mockito.when(invoiceHeaderService.createInvoiceHeader(any())).thenReturn(expectedInvoiceHeader);

        ResponseEntity<InvoiceHeader> response = invoiceHeaderController.save(expectedInvoiceHeader);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INV-12389", response.getBody().getNumber());
        assertEquals("Alex Juca", response.getBody().getCustomerName());
        assertEquals(invoiceDate, response.getBody().getDate());
        assertEquals(0, BigDecimal.ZERO.compareTo(response.getBody().getSubTotalAmount()));
        assertEquals(0, BigDecimal.ZERO.compareTo(response.getBody().getVatAmount()));
        assertEquals(0, BigDecimal.ZERO.compareTo(response.getBody().getTotalAmount()));
        assertNotNull(response.getBody().getInvoiceDetails());
        assertTrue(response.getBody().getInvoiceDetails().isEmpty());
    }
}