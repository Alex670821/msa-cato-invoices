package com.cato.msa.invoices.service.impl;

import com.cato.msa.invoices.domain.InvoiceHeader;
import com.cato.msa.invoices.repository.InvoiceHeaderRepository;
import com.cato.msa.invoices.service.InvoiceHeaderService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceHeaderServiceImpl implements InvoiceHeaderService {

    private final InvoiceHeaderRepository invoiceHeaderRepository;

    //Inyectar dependencia por eso se agrega el contructor sobrecargado
    public InvoiceHeaderServiceImpl(InvoiceHeaderRepository invoiceHeaderRepository) {
        this.invoiceHeaderRepository = invoiceHeaderRepository;
    }

    @Override
    public InvoiceHeader createInvoiceHeader(InvoiceHeader invoiceHeader) {
        invoiceHeader.calculateInvoiceAmounts();
        return invoiceHeaderRepository.save(invoiceHeader);
    }
}
