package com.cato.msa.invoices.service.impl;

import com.cato.msa.invoices.domain.InvoiceHeader;
import com.cato.msa.invoices.exception.NotContentException;
import com.cato.msa.invoices.exception.NotFoundException;
import com.cato.msa.invoices.repository.InvoiceHeaderRepository;
import com.cato.msa.invoices.service.InvoiceHeaderService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InvoiceHeaderServiceImpl implements InvoiceHeaderService {

    private final InvoiceHeaderRepository invoiceHeaderRepository;

    public InvoiceHeaderServiceImpl(InvoiceHeaderRepository invoiceHeaderRepository) {
        this.invoiceHeaderRepository = invoiceHeaderRepository;
    }

    @Override
    public InvoiceHeader createInvoiceHeader(InvoiceHeader invoiceHeader) {
        invoiceHeader.calculateInvoiceAmounts();
        return invoiceHeaderRepository.save(invoiceHeader);
    }

    @Override
    public List<InvoiceHeader> getAll() {
        List<InvoiceHeader> invoiceHeaders = invoiceHeaderRepository.findAll();
        if (invoiceHeaders.isEmpty()) {
            throw new NotContentException("Not Content");
        } else {
            return invoiceHeaders;
        }
    }

    @Override
    public InvoiceHeader getInvoiceByNumber(String number) {
        return invoiceHeaderRepository.findByNumber(number).orElseThrow(()-> new NotFoundException("Invoice with number '" + number + "' was not found"));
    }
    }



