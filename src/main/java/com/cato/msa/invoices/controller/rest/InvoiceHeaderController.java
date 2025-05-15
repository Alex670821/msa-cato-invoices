package com.cato.msa.invoices.controller.rest;

import com.cato.msa.invoices.controller.api.InvoiceHeaderApi;
import com.cato.msa.invoices.domain.InvoiceHeader;
import com.cato.msa.invoices.service.InvoiceHeaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceHeaderController implements InvoiceHeaderApi {

    private final InvoiceHeaderService invoiceHeaderService;

    //para inyectar la dependencia
    public InvoiceHeaderController(InvoiceHeaderService invoiceHeaderService) {
        this.invoiceHeaderService = invoiceHeaderService;
    }

    @Override
    public ResponseEntity<InvoiceHeader> save(InvoiceHeader invoiceHeader) {
        InvoiceHeader savedInvoiceHeader = invoiceHeaderService.createInvoiceHeader(invoiceHeader);
        return new ResponseEntity<>(savedInvoiceHeader, HttpStatus.CREATED);
    }
}
