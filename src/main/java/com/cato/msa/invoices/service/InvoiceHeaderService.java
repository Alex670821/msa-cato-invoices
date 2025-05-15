package com.cato.msa.invoices.service;

import com.cato.msa.invoices.domain.InvoiceHeader;

import java.util.List;

public interface InvoiceHeaderService {

    InvoiceHeader createInvoiceHeader(InvoiceHeader invoiceHeader);

    List<InvoiceHeader> getAll();
}
